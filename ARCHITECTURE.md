# Arquitetura do COMPANION

## Visão Geral

O COMPANION é construído com arquitetura modular e escalável, preparada para evolução futura para avatar 3D completo.

## Camadas Principais

### 1. App Mobile (Android)
- **Tecnologia**: Kotlin + Jetpack Compose
- **Arquitetura**: MVVM (Model-View-ViewModel)
- **Navegação**: Navigation Compose
- **Estado**: StateFlow / Flow
- **Armazenamento Local**: DataStore Preferences

### 2. Camada de Avatar
- **Estado Atual**: 2.5D / Estático
- **Preparação Futura**: Unity as a Library (UaaL)
- **Dados**: Armazenados no banco (avatar_name, avatar_style, etc.)
- **Customização**: Rosto, cabelo, corpo, estilo, nome, pronomes

### 3. Camada de Personalidade (IA)
- **Modelo**: OpenAI GPT-4 (ou GPT-3.5-turbo)
- **Prompt-base**: Permanente e consistente (`lib/ai/prompt-base.ts`)
- **Comportamento**: Empático, calmo, presente, não sedutor
- **Limites**: Claramente definidos e aplicados

### 4. Camada de Memória Emocional
- **Armazenamento**: PostgreSQL (JSON para flexibilidade)
- **Dados**: Nome, preferências, temas recorrentes, emoções, rotina
- **Evolução**: Atualizada a cada interação
- **Privacidade**: Totalmente privada

### 5. Camada de Comunidade (Fase 2)
- **Estrutura**: Preparada no banco de dados
- **Moderação**: IA automática + revisão manual
- **Regras**: Claramente definidas
- **Anonimato**: Apelidos, sem DM inicial

### 6. Backend
- **Tecnologia**: Node.js + Next.js (API Routes)
- **Banco**: PostgreSQL + Prisma ORM
- **Autenticação**: JWT
- **Validação**: Zod

## Fluxo de Dados

```
Usuário (Android)
    ↓
API Service (Retrofit)
    ↓
Backend API (Next.js)
    ↓
Companion AI (OpenAI)
    ↓
Memória Emocional (Prisma)
    ↓
Resposta → Usuário
```

## Estrutura de Pastas

```
companion/
├── backend/
│   ├── pages/api/          # Rotas da API
│   ├── lib/
│   │   ├── ai/             # IA e prompt-base
│   │   ├── auth.ts         # Autenticação
│   │   ├── middleware.ts   # Middleware de auth
│   │   └── prisma.ts       # Cliente Prisma
│   └── prisma/
│       └── schema.prisma   # Schema do banco
│
└── android/
    ├── app/src/main/java/com/companion/app/
    │   ├── data/
    │   │   ├── local/       # DataStore
    │   │   ├── model/       # Modelos de dados
    │   │   └── remote/      # API Service
    │   ├── ui/
    │   │   ├── screens/     # Telas
    │   │   ├── navigation/  # Navegação
    │   │   └── theme/       # Tema
    │   └── viewmodel/       # ViewModels
```

## Preparação para Avatar 3D

### Estrutura Atual
- Avatar é uma entidade no banco de dados
- Customização via campos (avatar_style, avatar_body_type, etc.)
- Renderização 2.5D/estática no app

### Migração Futura (Unity)
1. **Unity as a Library (UaaL)**
   - Unity exporta como biblioteca Android
   - Integração via JNI
   - Renderização 3D no app

2. **Humanoid Rig**
   - Avatar 3D com rigging humanoide
   - Animações faciais e corporais
   - Expressões emocionais

3. **Dados de Avatar**
   - Mesma estrutura de dados
   - Renderização muda de 2D para 3D
   - Sem mudanças no backend

## Escalabilidade

### Backend
- Prisma permite migrações fáceis
- API stateless (JWT)
- Preparado para horizontal scaling

### Android
- Arquitetura MVVM facilita testes
- Separação de responsabilidades
- Preparado para múltiplos módulos

### IA
- Prompt-base centralizado
- Fácil troca de modelo (OpenAI, Anthropic, etc.)
- Cache de respostas (futuro)

## Segurança

- Autenticação JWT
- Senhas hasheadas (bcrypt)
- Validação de dados (Zod)
- Detecção de conteúdo inapropriado
- Logs mínimos (LGPD)

## Próximos Passos

1. **Avatar 3D**
   - Integração Unity
   - Humanoid rig
   - Animações

2. **Comunidade**
   - Implementação completa
   - Moderação automática
   - Salas temáticas

3. **Melhorias de IA**
   - Cache de respostas
   - Personalização mais profunda
   - Análise de sentimento avançada

