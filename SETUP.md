# Guia de Setup - COMPANION

Este guia explica como configurar e executar o projeto COMPANION do zero.

## Pré-requisitos

### Backend
- Node.js 18+ e npm
- PostgreSQL 14+
- Conta OpenAI (para API key)

### Android
- Android Studio Hedgehog (2023.1.1) ou superior
- JDK 17
- Android SDK 24+ (minSdk)

## Setup do Backend

### 1. Instalar dependências
```bash
cd backend
npm install
```

### 2. Configurar variáveis de ambiente
```bash
cp .env.example .env
```

Edite o arquivo `.env` com suas configurações:
```env
DATABASE_URL="postgresql://user:password@localhost:5432/companion?schema=public"
JWT_SECRET="seu-secret-jwt-aqui"
JWT_EXPIRES_IN="7d"
OPENAI_API_KEY="sua-openai-api-key"
NODE_ENV="development"
PORT=3000
```

### 3. Configurar banco de dados
```bash
# Gerar cliente Prisma
npx prisma generate

# Criar banco de dados (se ainda não existir)
# No PostgreSQL:
# CREATE DATABASE companion;

# Executar migrações
npx prisma migrate dev --name init
```

### 4. Executar servidor
```bash
npm run dev
```

O servidor estará rodando em `http://localhost:3000`

## Setup do Android

### 1. Abrir projeto
1. Abra o Android Studio
2. File → Open → Selecione a pasta `android`
3. Aguarde o Gradle sincronizar

### 2. Configurar API URL
Edite `android/app/src/main/java/com/companion/app/data/remote/RetrofitClient.kt`:

Para emulador:
```kotlin
private const val BASE_URL = "http://10.0.2.2:3000/api/"
```

Para dispositivo físico (mesma rede):
```kotlin
private const val BASE_URL = "http://SEU_IP_LOCAL:3000/api/"
```

### 3. Build e Run
1. Conecte um dispositivo ou inicie um emulador
2. Clique em Run (▶️) ou pressione Shift+F10

## Estrutura do Projeto

```
companion/
├── backend/              # API Backend
│   ├── pages/api/       # Rotas da API
│   ├── lib/             # Lógica de negócio
│   └── prisma/          # Schema do banco
│
├── android/              # App Android
│   └── app/src/main/    # Código fonte
│
└── docs/                # Documentação
```

## Testando o Fluxo Completo

### 1. Registrar usuário
```bash
POST http://localhost:3000/api/auth/register
{
  "email": "teste@example.com",
  "password": "senha123",
  "name": "Teste",
  "age": 25
}
```

### 2. Fazer login
```bash
POST http://localhost:3000/api/auth/login
{
  "email": "teste@example.com",
  "password": "senha123"
}
```

### 3. Completar onboarding (via app ou API)
```bash
POST http://localhost:3000/api/onboarding/complete
Authorization: Bearer <token>
{
  "companionType": "FRIEND",
  "conversationTone": "Calmo",
  "avatar": {
    "avatarName": "Alex",
    "avatarStyle": "Casual",
    "avatarBodyType": "Médio",
    "avatarFaceType": "Oval",
    "avatarHair": "Curto",
    "avatarSkinTone": "Médio",
    "pronouns": "ele/dele"
  }
}
```

### 4. Enviar mensagem
```bash
POST http://localhost:3000/api/companion/chat
Authorization: Bearer <token>
{
  "message": "Oi, como você está?"
}
```

## Troubleshooting

### Backend não conecta ao banco
- Verifique se o PostgreSQL está rodando
- Confirme a `DATABASE_URL` no `.env`
- Teste a conexão: `psql -U user -d companion`

### Android não conecta à API
- Verifique se o backend está rodando
- Confirme a `BASE_URL` no `RetrofitClient.kt`
- Para dispositivo físico, use o IP da sua máquina na rede local
- Verifique o firewall

### Erro de migração Prisma
```bash
# Resetar banco (CUIDADO: apaga todos os dados)
npx prisma migrate reset

# Ou criar nova migração
npx prisma migrate dev --name <nome>
```

### Erro de compilação Android
- Limpe o projeto: Build → Clean Project
- Sincronize Gradle: File → Sync Project with Gradle Files
- Invalide cache: File → Invalidate Caches / Restart

## Próximos Passos

1. **Configurar avatar visual** (2.5D/estático)
2. **Testar fluxo completo** no app
3. **Ajustar prompt-base** conforme necessário
4. **Preparar para produção** (variáveis de ambiente, segurança)

## Suporte

Para dúvidas ou problemas, consulte:
- `ARCHITECTURE.md` - Arquitetura do projeto
- `SECURITY.md` - Segurança e ética
- `PROMPT_BASE.md` - Prompt-base da IA
- `backend/README.md` - Documentação do backend


