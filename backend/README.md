# COMPANION Backend

API backend para o aplicativo COMPANION.

## Setup

1. Instale as dependências:
```bash
npm install
```

2. Configure as variáveis de ambiente:
```bash
cp .env.example .env
# Edite o .env com suas configurações
```

3. Configure o banco de dados:
```bash
npx prisma generate
npx prisma migrate dev
```

4. Execute o servidor:
```bash
npm run dev
```

## Estrutura

- `pages/api/` - Rotas da API
- `lib/` - Utilitários e lógica de negócio
- `lib/ai/` - Integração com IA e prompt-base
- `prisma/` - Schema do banco de dados

## Endpoints

### Autenticação
- `POST /api/auth/register` - Registrar novo usuário
- `POST /api/auth/login` - Login

### Onboarding
- `POST /api/onboarding/complete` - Completar onboarding

### Companion
- `POST /api/companion/chat` - Enviar mensagem e receber resposta
- `GET /api/companion/memory` - Obter memória emocional

### Avatar
- `PUT /api/avatar/update` - Atualizar avatar

### Report
- `POST /api/report/create` - Criar denúncia

## Prompt-base da IA

O prompt-base permanente está em `lib/ai/prompt-base.ts` e define:
- Personalidade do Companion
- Limites e regras obrigatórias
- Tom de voz
- Comportamento empático

## Segurança

- Autenticação JWT
- Validação de dados com Zod
- Detecção de conteúdo inapropriado
- Detecção de sofrimento emocional intenso
- Mensagens de apoio quando necessário


