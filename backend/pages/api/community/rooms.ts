import type { NextApiRequest, NextApiResponse } from 'next'
import { prisma } from '@/lib/prisma'

/**
 * GET /api/community/rooms
 * Lista todas as salas da comunidade ativas
 * 
 * FASE 2 - Preparado mas não implementado completamente
 */
export default async function handler(
  req: NextApiRequest,
  res: NextApiResponse
) {
  if (req.method !== 'GET') {
    return res.status(405).json({ error: 'Method not allowed' })
  }

  try {
    const rooms = await prisma.communityRoom.findMany({
      where: { isActive: true },
      orderBy: { createdAt: 'desc' },
      select: {
        id: true,
        name: true,
        description: true,
        theme: true,
        createdAt: true,
      },
    })

    return res.status(200).json({ rooms })
  } catch (error) {
    console.error('Erro ao buscar salas:', error)
    return res.status(500).json({ error: 'Erro interno do servidor' })
  }
}


