/**
 * Moderação de Comunidade (Fase 2)
 * 
 * Este módulo prepara a estrutura para moderação automática
 * de mensagens na comunidade usando IA.
 */

import { detectInappropriateContent, detectEmotionalDistress } from './prompt-base'

export interface ModerationResult {
  isApproved: boolean
  reason?: string
  requiresReview: boolean
}

/**
 * Modera uma mensagem da comunidade
 */
export function moderateCommunityMessage(message: string): ModerationResult {
  // Detectar conteúdo inapropriado
  if (detectInappropriateContent(message)) {
    return {
      isApproved: false,
      reason: 'Conteúdo inapropriado detectado',
      requiresReview: false,
    }
  }
  
  // Detectar tentativa de troca de contatos externos
  if (detectExternalContactSharing(message)) {
    return {
      isApproved: false,
      reason: 'Compartilhamento de contatos externos não permitido',
      requiresReview: false,
    }
  }
  
  // Detectar assédio
  if (detectHarassment(message)) {
    return {
      isApproved: false,
      reason: 'Possível assédio detectado',
      requiresReview: true,
    }
  }
  
  return {
    isApproved: true,
    requiresReview: false,
  }
}

/**
 * Detecta tentativas de compartilhar contatos externos
 */
function detectExternalContactSharing(message: string): boolean {
  const patterns = [
    /@\w+/g, // Email
    /\+?\d{10,}/g, // Telefone
    /(instagram|facebook|twitter|whatsapp|telegram|discord)/i,
    /(\.com|\.net|\.org|\.br)/i, // URLs
  ]
  
  return patterns.some(pattern => pattern.test(message))
}

/**
 * Detecta possível assédio
 */
function detectHarassment(message: string): boolean {
  const harassmentPatterns = [
    /(idiota|imbecil|burro|estúpido)/i,
    /(vai se foder|vai tomar no cu|foda-se)/i,
    // Adicione mais padrões conforme necessário
  ]
  
  return harassmentPatterns.some(pattern => pattern.test(message))
}

/**
 * Regras da Comunidade
 */
export const COMMUNITY_RULES = `
REGRAS DA COMUNIDADE COMPANION

1. Respeito: Trate todos com respeito e empatia
2. Privacidade: Não compartilhe informações pessoais ou contatos externos
3. Conteúdo: Não poste conteúdo sexual, ofensivo ou inapropriado
4. Apoio: Este é um espaço de apoio emocional, não de assédio
5. Anonimato: Use seu apelido, não compartilhe sua identidade real

Violações podem resultar em banimento permanente.
`


