import type { NextApiRequest, NextApiResponse } from 'next'
import { prisma } from '@/lib/prisma'
import { withAuth, AuthenticatedRequest } from '@/lib/middleware'
import { z } from 'zod'

const onboardingSchema = z.object({
  companionType: z.enum(['FRIEND', 'CONFIDANT', 'NEUTRAL']),
  conversationTone: z.string(),
  avatar: z.object({
    avatarName: z.string(),
    avatarStyle: z.string(),
    avatarBodyType: z.string(),
    avatarFaceType: z.string(),
    avatarHair: z.string(),
    avatarSkinTone: z.string(),
    pronouns: z.string().optional(),
  }),
})

async function handler(req: AuthenticatedRequest, res: NextApiResponse) {
  if (req.method !== 'POST') {
    return res.status(405).json({ error: 'Method not allowed' })
  }

  try {
    const userId = req.userId!
    const data = onboardingSchema.parse(req.body)

    // Criar preferências do companion
    await prisma.companionPreferences.create({
      data: {
        userId,
        companionType: data.companionType,
        conversationTone: data.conversationTone,
      },
    })

    // Criar avatar
    await prisma.avatar.create({
      data: {
        userId,
        ...data.avatar,
      },
    })

    // Criar memória inicial
    await prisma.memory.create({
      data: {
        userId,
        userName: data.avatar.avatarName, // Nome do avatar como referência inicial
      },
    })

    return res.status(200).json({ success: true })
  } catch (error: any) {
    if (error.name === 'ZodError') {
      return res.status(400).json({ error: error.errors[0].message })
    }
    console.error('Erro no onboarding:', error)
    return res.status(500).json({ error: 'Erro interno do servidor' })
  }
}

export default withAuth(handler)


