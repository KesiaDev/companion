import type { NextApiRequest, NextApiResponse } from 'next'
import { prisma } from '@/lib/prisma'
import { withAuth, AuthenticatedRequest } from '@/lib/middleware'

async function handler(req: AuthenticatedRequest, res: NextApiResponse) {
  if (req.method !== 'GET') {
    return res.status(405).json({ error: 'Method not allowed' })
  }

  try {
    const userId = req.userId!

    const memory = await prisma.memory.findUnique({
      where: { userId },
    })

    if (!memory) {
      return res.status(404).json({ error: 'Memória não encontrada' })
    }

    return res.status(200).json({
      userName: memory.userName,
      preferences: memory.preferences,
      recurringThemes: memory.recurringThemes,
      frequentEmotions: memory.frequentEmotions,
      routine: memory.routine,
      lastInteraction: memory.lastInteraction,
      interactionCount: memory.interactionCount,
    })
  } catch (error) {
    console.error('Erro ao buscar memória:', error)
    return res.status(500).json({ error: 'Erro interno do servidor' })
  }
}

export default withAuth(handler)

