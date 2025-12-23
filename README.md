# COMPANION — Companhia para o seu dia

Aplicativo de companhia emocional contínua para adultos (18+).

## Arquitetura

```
companion/
├── backend/          # API Node.js + Next.js
├── android/          # App Android (Kotlin + Jetpack Compose)
├── ios/              # App iOS (Swift + SwiftUI) - requer Mac para compilar
└── shared/           # Tipos e contratos compartilhados
```

## Stack Técnica

### Backend
- Node.js + Next.js (API Routes)
- Prisma ORM
- PostgreSQL
- Autenticação JWT
- Integração com modelo de linguagem (IA)

### Android
- Kotlin
- Jetpack Compose
- Arquitetura MVVM
- Preparado para Unity (avatar 3D futuro)

## Princípios do Produto

- **Companhia emocional**, não sexual
- **Presença constante** ao longo do dia
- **Espaço seguro** de conversa
- **Apoio cotidiano** sem criar dependência

## Estrutura Modular

1. **App Mobile** - Interface do usuário
2. **Camada de Avatar** - Visual (2.5D → 3D futuro)
3. **Camada de Personalidade** - IA empática
4. **Camada de Memória Emocional** - Contexto e preferências
5. **Camada de Comunidade** - Conexões seguras (fase 2)
6. **Backend** - API + banco de dados

## Desenvolvimento

### Backend
```bash
cd backend
npm install
npx prisma migrate dev
npm run dev
```

### Android
```bash
cd android
./gradlew build
```

## Segurança e Ética

- LGPD compliance
- Logs mínimos
- Botão de denúncia
- Mensagens de apoio em situações de sofrimento
- Sugestão de ajuda humana quando necessário


