# Segurança e Ética - COMPANION

## Princípios de Segurança

### LGPD Compliance
- Dados pessoais são coletados apenas quando necessário
- Usuários têm controle sobre seus dados
- Logs mínimos são mantidos (apenas para debugging e segurança)
- Dados sensíveis são criptografados

### Autenticação
- Senhas são hasheadas com bcrypt (10 rounds)
- Tokens JWT com expiração
- Tokens são armazenados localmente no dispositivo (DataStore)

### Privacidade
- Conversas são armazenadas no banco, mas podem ser deletadas pelo usuário
- Memória emocional é privada e não compartilhada
- Avatar é personalizável mas não identifica o usuário

## Sistema de Denúncia

### Tipos de Denúncia
1. **Conteúdo Inapropriado** - Conteúdo sexual ou ofensivo
2. **Sofrimento Emocional** - Usuário em crise
3. **Outros** - Outras questões

### Fluxo de Denúncia
1. Usuário pode denunciar através do botão de denúncia
2. Denúncia é registrada no banco de dados
3. Equipe de moderação revisa (futuro)
4. Ações apropriadas são tomadas

## Apoio Emocional

### Detecção Automática
O sistema detecta automaticamente:
- Conteúdo sexual explícito
- Sofrimento emocional intenso (suicídio, depressão profunda, etc.)

### Respostas Automáticas
- **Conteúdo Sexual**: Redirecionamento respeitoso para o emocional
- **Sofrimento Intenso**: Mensagem de apoio com recursos de ajuda profissional

### Recursos de Apoio
- CVV (Centro de Valorização da Vida): 188
- Sugestão de busca por ajuda profissional (psicólogo/psiquiatra)

## Limites do Companion

O Companion NUNCA:
- Gera conteúdo sexual explícito
- Participa de fantasias sexuais
- Incentiva masturbação
- Atua como terapeuta
- Cria dependência emocional
- Descreve o corpo de forma erótica

O Companion SEMPRE:
- Redireciona tentativas de erotização para o emocional
- Mantém limites claros e respeitosos
- Sugere ajuda profissional quando necessário
- Respeita a privacidade do usuário

## Logs e Monitoramento

### Logs Mínimos
- Erros técnicos
- Tentativas de autenticação falhadas
- Denúncias (sem conteúdo sensível)

### O que NÃO é logado
- Conteúdo completo das conversas
- Dados sensíveis do usuário
- Emoções específicas (apenas agregadas)

## Contato

Para questões de segurança ou ética, entre em contato através do app (botão de denúncia) ou diretamente com a equipe.


