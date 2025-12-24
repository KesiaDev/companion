import type { NextApiRequest, NextApiResponse } from 'next'
import { prisma } from '@/lib/prisma'
import { withAuth, AuthenticatedRequest } from '@/lib/middleware'
import { z } from 'zod'

const reportSchema = z.object({
  type: z.enum(['inappropriate_content', 'emotional_distress', 'other']),
  description: z.string().optional(),
  context: z.any().optional(),
})

async function handler(req: AuthenticatedRequest, res: NextApiResponse) {
  if (req.method !== 'POST') {
    return res.status(405).json({ error: 'Method not allowed' })
  }

  try {
    const userId = req.userId!
    const data = reportSchema.parse(req.body)

    const report = await prisma.report.create({
      data: {
        userId,
        type: data.type,
        description: data.description,
        context: data.context ? JSON.stringify(data.context) : null,
      },
    })

    return res.status(201).json({ reportId: report.id, success: true })
  } catch (error: any) {
    if (error.name === 'ZodError') {
      return res.status(400).json({ error: error.errors[0].message })
    }
    console.error('Erro ao criar report:', error)
    return res.status(500).json({ error: 'Erro interno do servidor' })
  }
}

export default withAuth(handler)


