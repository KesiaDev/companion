import type { NextApiRequest, NextApiResponse } from 'next'
import { verifyToken } from './auth'

export interface AuthenticatedRequest extends NextApiRequest {
  userId?: string
}

export function withAuth(
  handler: (req: AuthenticatedRequest, res: NextApiResponse) => Promise<void>
) {
  return async (req: AuthenticatedRequest, res: NextApiResponse) => {
    const token = req.headers.authorization?.replace('Bearer ', '')

    if (!token) {
      return res.status(401).json({ error: 'Token não fornecido' })
    }

    const decoded = verifyToken(token)
    if (!decoded) {
      return res.status(401).json({ error: 'Token inválido' })
    }

    req.userId = decoded.userId
    return handler(req, res)
  }
}


