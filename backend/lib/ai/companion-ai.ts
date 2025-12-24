import OpenAI from 'openai'
import { COMPANION_SYSTEM_PROMPT, detectInappropriateContent, detectEmotionalDistress, getSupportMessage } from './prompt-base'
import { prisma } from '../prisma'

const openai = new OpenAI({
  apiKey: process.env.OPENAI_API_KEY,
})

interface CompanionContext {
  userName?: string
  companionType?: string
  conversationTone?: string
  preferences?: any
  recurringThemes?: any
  frequentEmotions?: any
  routine?: any
  recentConversations?: Array<{ userMessage: string; companionResponse: string }>
}

/**
 * Gera resposta do Companion baseada na mensagem do usuário
 */
export async function generateCompanionResponse(
  userId: string,
  userMessage: string
): Promise<{ response: string; emotion?: string; requiresSupport?: boolean }> {
  // Verificar conteúdo inapropriado
  if (detectInappropriateContent(userMessage)) {
    return {
      response: 'Entendo que você esteja buscando proximidade agora. Posso estar aqui para conversar e te apoiar emocionalmente, mas não participo de interações sexuais. Como você está se sentindo hoje? Há algo que gostaria de compartilhar?',
      emotion: 'redirected',
    }
  }

  // Verificar sofrimento emocional intenso
  if (detectEmotionalDistress(userMessage)) {
    return {
      response: getSupportMessage(),
      emotion: 'distress',
      requiresSupport: true,
    }
  }

  // Buscar contexto do usuário
  const context = await getCompanionContext(userId)

  // Construir prompt com contexto
  const messages: OpenAI.Chat.Completions.ChatCompletionMessageParam[] = [
    {
      role: 'system',
      content: COMPANION_SYSTEM_PROMPT,
    },
    {
      role: 'system',
      content: buildContextPrompt(context),
    },
    ...(context.recentConversations?.slice(-5).map(conv => ({
      role: 'assistant' as const,
      content: conv.companionResponse,
    })) || []),
    {
      role: 'user',
      content: userMessage,
    },
  ]

  try {
    const completion = await openai.chat.completions.create({
      model: 'gpt-4-turbo-preview', // ou 'gpt-3.5-turbo' para custo menor
      messages,
      temperature: 0.7,
      max_tokens: 500,
    })

    const response = completion.choices[0]?.message?.content || 'Desculpe, não consegui processar isso agora. Como você está se sentindo?'

    // Detectar emoção na mensagem do usuário (simplificado)
    const emotion = detectEmotion(userMessage)

    return {
      response,
      emotion,
    }
  } catch (error) {
    console.error('Erro ao gerar resposta do Companion:', error)
    return {
      response: 'Desculpe, estou tendo dificuldades técnicas. Como você está se sentindo hoje?',
      emotion: 'neutral',
    }
  }
}

/**
 * Busca contexto do usuário para personalizar respostas
 */
async function getCompanionContext(userId: string): Promise<CompanionContext> {
  const [user, memory, preferences, recentConversations] = await Promise.all([
    prisma.user.findUnique({
      where: { id: userId },
      include: { avatar: true },
    }),
    prisma.memory.findUnique({
      where: { userId },
    }),
    prisma.companionPreferences.findUnique({
      where: { userId },
    }),
    prisma.conversation.findMany({
      where: { userId },
      orderBy: { createdAt: 'desc' },
      take: 5,
      select: {
        userMessage: true,
        companionResponse: true,
      },
    }),
  ])

  // Parse JSON strings para objetos
  const parseJsonField = (field: string | null | undefined): any => {
    if (!field) return undefined
    try {
      return JSON.parse(field)
    } catch {
      return undefined
    }
  }

  return {
    userName: user?.name || memory?.userName || undefined,
    companionType: preferences?.companionType || undefined,
    conversationTone: preferences?.conversationTone || undefined,
    preferences: parseJsonField(memory?.preferences),
    recurringThemes: parseJsonField(memory?.recurringThemes),
    frequentEmotions: parseJsonField(memory?.frequentEmotions),
    routine: parseJsonField(memory?.routine),
    recentConversations: recentConversations.reverse(),
  }
}

/**
 * Constrói prompt de contexto para a IA
 */
function buildContextPrompt(context: CompanionContext): string {
  let prompt = '## CONTEXTO DO USUÁRIO\n\n'

  if (context.userName) {
    prompt += `O nome do usuário é ${context.userName}.\n`
  }

  if (context.companionType) {
    prompt += `Tipo de companhia escolhida: ${context.companionType}.\n`
  }

  if (context.conversationTone) {
    prompt += `Tom de conversa preferido: ${context.conversationTone}.\n`
  }

  if (context.preferences) {
    prompt += `Preferências: ${JSON.stringify(context.preferences)}\n`
  }

  if (context.routine) {
    prompt += `Rotina: ${JSON.stringify(context.routine)}\n`
  }

  return prompt
}

/**
 * Detecta emoção na mensagem (simplificado - pode ser melhorado)
 */
function detectEmotion(message: string): string {
  const emotions: Record<string, RegExp[]> = {
    happy: [/feliz|alegre|contente|animad[ao]|bem/i],
    sad: [/triste|tristeza|chatead[ao]|mal/i],
    anxious: [/ansios[ao]|preocupad[ao]|nervos[ao]/i],
    calm: [/calm[ao]|tranquil[ao]|sereno/i],
    tired: [/cansad[ao]|exaust[ao]|fadiga/i],
  }

  for (const [emotion, patterns] of Object.entries(emotions)) {
    if (patterns.some(pattern => pattern.test(message))) {
      return emotion
    }
  }

  return 'neutral'
}

/**
 * Atualiza memória emocional do usuário
 */
export async function updateEmotionalMemory(
  userId: string,
  emotion: string,
  theme?: string
): Promise<void> {
  const memory = await prisma.memory.findUnique({
    where: { userId },
  })

  // Parse JSON strings para objetos
  const parseJsonField = (field: string | null | undefined): Record<string, number> => {
    if (!field) return {}
    try {
      return JSON.parse(field)
    } catch {
      return {}
    }
  }

  if (!memory) {
    await prisma.memory.create({
      data: {
        userId,
        frequentEmotions: JSON.stringify({ [emotion]: 1 }),
        recurringThemes: JSON.stringify(theme ? { [theme]: 1 } : {}),
        interactionCount: 1,
        lastInteraction: new Date(),
      },
    })
    return
  }

  const currentEmotions = parseJsonField(memory.frequentEmotions)
  const currentThemes = parseJsonField(memory.recurringThemes)

  currentEmotions[emotion] = (currentEmotions[emotion] || 0) + 1
  if (theme) {
    currentThemes[theme] = (currentThemes[theme] || 0) + 1
  }

  await prisma.memory.update({
    where: { userId },
    data: {
      frequentEmotions: JSON.stringify(currentEmotions),
      recurringThemes: JSON.stringify(currentThemes),
      interactionCount: memory.interactionCount + 1,
      lastInteraction: new Date(),
    },
  })
}


