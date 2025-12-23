import type { NextApiRequest, NextApiResponse } from 'next'
import { prisma } from '@/lib/prisma'
import { hashPassword, generateToken, registerSchema } from '@/lib/auth'

export default async function handler(
  req: NextApiRequest,
  res: NextApiResponse
) {
  if (req.method !== 'POST') {
    return res.status(405).json({ error: 'Method not allowed' })
  }

  try {
    const data = registerSchema.parse(req.body)

    // Verificar se usuário já existe
    const existingUser = await prisma.user.findUnique({
      where: { email: data.email },
    })

    if (existingUser) {
      return res.status(400).json({ error: 'Email já cadastrado' })
    }

    // Criar usuário
    const passwordHash = await hashPassword(data.password)
    const user = await prisma.user.create({
      data: {
        email: data.email,
        passwordHash,
        name: data.name,
        age: data.age,
      },
      select: {
        id: true,
        email: true,
        name: true,
        createdAt: true,
      },
    })

    // Gerar token
    const token = generateToken(user.id)

    return res.status(201).json({
      user,
      token,
    })
  } catch (error: any) {
    if (error.name === 'ZodError') {
      return res.status(400).json({ error: error.errors[0].message })
    }
    console.error('Erro no registro:', error)
    return res.status(500).json({ error: 'Erro interno do servidor' })
  }
}

