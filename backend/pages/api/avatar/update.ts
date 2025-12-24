import type { NextApiRequest, NextApiResponse } from 'next'
import { prisma } from '@/lib/prisma'
import { withAuth, AuthenticatedRequest } from '@/lib/middleware'
import { z } from 'zod'

const avatarUpdateSchema = z.object({
  avatarName: z.string().optional(),
  avatarStyle: z.string().optional(),
  avatarBodyType: z.string().optional(),
  avatarFaceType: z.string().optional(),
  avatarHair: z.string().optional(),
  avatarSkinTone: z.string().optional(),
  pronouns: z.string().optional(),
})

async function handler(req: AuthenticatedRequest, res: NextApiResponse) {
  if (req.method !== 'PUT') {
    return res.status(405).json({ error: 'Method not allowed' })
  }

  try {
    const userId = req.userId!
    const data = avatarUpdateSchema.parse(req.body)

    const avatar = await prisma.avatar.update({
      where: { userId },
      data,
    })

    return res.status(200).json(avatar)
  } catch (error: any) {
    if (error.name === 'ZodError') {
      return res.status(400).json({ error: error.errors[0].message })
    }
    console.error('Erro ao atualizar avatar:', error)
    return res.status(500).json({ error: 'Erro interno do servidor' })
  }
}

export default withAuth(handler)


