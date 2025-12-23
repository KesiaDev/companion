import type { NextApiRequest, NextApiResponse } from 'next'
import { prisma } from '@/lib/prisma'
import { verifyPassword, generateToken, loginSchema } from '@/lib/auth'

export default async function handler(
  req: NextApiRequest,
  res: NextApiResponse
) {
  if (req.method !== 'POST') {
    return res.status(405).json({ error: 'Method not allowed' })
  }

  try {
    const data = loginSchema.parse(req.body)

    // Buscar usuário
    const user = await prisma.user.findUnique({
      where: { email: data.email },
    })

    if (!user) {
      return res.status(401).json({ error: 'Email ou senha inválidos' })
    }

    // Verificar senha
    const isValid = await verifyPassword(data.password, user.passwordHash)
    if (!isValid) {
      return res.status(401).json({ error: 'Email ou senha inválidos' })
    }

    // Gerar token
    const token = generateToken(user.id)

    return res.status(200).json({
      user: {
        id: user.id,
        email: user.email,
        name: user.name,
      },
      token,
    })
  } catch (error: any) {
    if (error.name === 'ZodError') {
      return res.status(400).json({ error: error.errors[0].message })
    }
    console.error('Erro no login:', error)
    return res.status(500).json({ error: 'Erro interno do servidor' })
  }
}

