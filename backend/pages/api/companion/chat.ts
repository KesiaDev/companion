import type { NextApiRequest, NextApiResponse } from 'next'
import { prisma } from '@/lib/prisma'
import { withAuth, AuthenticatedRequest } from '@/lib/middleware'
import { generateCompanionResponse, updateEmotionalMemory } from '@/lib/ai/companion-ai'
import { z } from 'zod'

const chatSchema = z.object({
  message: z.string().min(1, 'Mensagem não pode estar vazia'),
})

async function handler(req: AuthenticatedRequest, res: NextApiResponse) {
  if (req.method !== 'POST') {
    return res.status(405).json({ error: 'Method not allowed' })
  }

  try {
    const userId = req.userId!
    const { message } = chatSchema.parse(req.body)

    // Gerar resposta do Companion
    const { response, emotion, requiresSupport } = await generateCompanionResponse(
      userId,
      message
    )

    // Salvar conversa
    const conversation = await prisma.conversation.create({
      data: {
        userId,
        userMessage: message,
        companionResponse: response,
        emotion: emotion || undefined,
      },
    })

    // Atualizar memória emocional
    if (emotion) {
      await updateEmotionalMemory(userId, emotion)
    }

    return res.status(200).json({
      response,
      emotion,
      requiresSupport,
      conversationId: conversation.id,
    })
  } catch (error: any) {
    if (error.name === 'ZodError') {
      return res.status(400).json({ error: error.errors[0].message })
    }
    console.error('Erro no chat:', error)
    return res.status(500).json({ error: 'Erro interno do servidor' })
  }
}

export default withAuth(handler)


