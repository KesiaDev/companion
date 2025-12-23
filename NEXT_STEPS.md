# Próximos Passos - COMPANION

Guia prático para colocar o projeto em funcionamento.

## 🎯 Prioridade 1: Configurar e Testar o Backend

### Passo 1: Instalar dependências do backend
```bash
cd backend
npm install
```

### Passo 2: Configurar variáveis de ambiente
Crie o arquivo `.env` na pasta `backend/`:

```env
DATABASE_URL="postgresql://usuario:senha@localhost:5432/companion?schema=public"
JWT_SECRET="seu-secret-jwt-super-seguro-aqui-mude-isso"
JWT_EXPIRES_IN="7d"
OPENAI_API_KEY="sk-sua-chave-openai-aqui"
NODE_ENV="development"
PORT=3000
```

**Onde conseguir:**
- **PostgreSQL**: Instale localmente ou use um serviço como [Supabase](https://supabase.com) (grátis)
- **OpenAI API Key**: Crie em [platform.openai.com/api-keys](https://platform.openai.com/api-keys)

### Passo 3: Configurar banco de dados
```bash
# Se usar PostgreSQL local, crie o banco:
# psql -U postgres
# CREATE DATABASE companion;

# Gerar cliente Prisma
npx prisma generate

# Executar migrações
npx prisma migrate dev --name init
```

### Passo 4: Testar o backend
```bash
npm run dev
```

Teste se está funcionando:
```bash
# Em outro terminal:
curl http://localhost:3000/api/auth/register -X POST -H "Content-Type: application/json" -d "{\"email\":\"teste@teste.com\",\"password\":\"senha123\"}"
```

---

## 🎯 Prioridade 2: Configurar e Testar o App Android

### Passo 1: Abrir projeto no Android Studio
1. Abra o Android Studio
2. File → Open → Selecione a pasta `android`
3. Aguarde o Gradle sincronizar (pode demorar na primeira vez)

### Passo 2: Configurar URL da API
Edite: `android/app/src/main/java/com/companion/app/data/remote/RetrofitClient.kt`

**Para emulador Android:**
```kotlin
private const val BASE_URL = "http://10.0.2.2:3000/api/"
```

**Para dispositivo físico (mesma rede WiFi):**
```kotlin
// Descubra seu IP local (Windows: ipconfig, Linux/Mac: ifconfig)
private const val BASE_URL = "http://192.168.1.XXX:3000/api/"
```

### Passo 3: Executar o app
1. Conecte um dispositivo ou inicie um emulador
2. Clique em Run (▶️)
3. Teste o fluxo completo de onboarding

---

## 🎯 Prioridade 3: Melhorias Imediatas

### 1. Completar integração do Onboarding
O `AvatarCreationScreen` precisa salvar via API. Implemente:

```kotlin
// Em AvatarCreationScreen.kt, adicione:
val context = LocalContext.current
val preferencesManager = PreferencesManager(context)
val token = runBlocking { preferencesManager.token.first() }

val onboardingViewModel = remember { 
    OnboardingViewModel(RetrofitClient.apiService, preferencesManager, token ?: "")
}

// No onClick do botão:
onboardingViewModel.completeOnboarding(
    companionType = "FRIEND", // Pegar do estado anterior
    conversationTone = "Calmo", // Pegar do estado anterior
    avatar = AvatarData(...)
)
```

### 2. Adicionar tratamento de erros
- Mostrar mensagens de erro amigáveis
- Tratar erros de rede
- Loading states visuais

### 3. Melhorar UI do Avatar
- Adicionar preview visual do avatar
- Ícones/ilustrações para opções
- Validação visual

---

## 🎯 Prioridade 4: Funcionalidades Adicionais

### 1. Avatar Visual (2.5D/Estático)
- Criar componentes visuais do avatar
- Renderizar baseado nas escolhas do usuário
- Preparar estrutura para Unity (futuro)

### 2. Histórico de Conversas
- Salvar conversas localmente
- Mostrar histórico na tela de chat
- Buscar conversas antigas

### 3. Melhorias na IA
- Ajustar prompt-base conforme feedback
- Adicionar mais contexto na memória
- Personalização mais profunda

### 4. Sistema de Denúncia
- Botão de denúncia na UI
- Integração completa com backend
- Feedback ao usuário após denúncia

---

## 🎯 Prioridade 5: Preparação para Produção

### 1. Segurança
- [ ] Revisar todas as variáveis de ambiente
- [ ] Configurar HTTPS
- [ ] Implementar rate limiting
- [ ] Adicionar CORS adequado
- [ ] Revisar logs (LGPD)

### 2. Performance
- [ ] Otimizar queries do banco
- [ ] Adicionar cache de respostas da IA
- [ ] Otimizar imagens/assets
- [ ] Implementar paginação

### 3. Testes
- [ ] Testes unitários (backend)
- [ ] Testes de integração (API)
- [ ] Testes de UI (Android)
- [ ] Testes de fluxo completo

### 4. Deploy
- [ ] Backend: Vercel, Railway, ou AWS
- [ ] Banco: Supabase, AWS RDS, ou PostgreSQL gerenciado
- [ ] Android: Google Play Store

---

## 📋 Checklist Rápido

### Backend
- [ ] `npm install` executado
- [ ] Arquivo `.env` configurado
- [ ] PostgreSQL rodando
- [ ] Migrações executadas
- [ ] Servidor rodando em `localhost:3000`
- [ ] Teste de registro funcionando

### Android
- [ ] Projeto aberto no Android Studio
- [ ] Gradle sincronizado
- [ ] URL da API configurada
- [ ] App compila sem erros
- [ ] App roda no dispositivo/emulador
- [ ] Conecta ao backend

### Teste Completo
- [ ] Registrar usuário
- [ ] Fazer login
- [ ] Completar onboarding
- [ ] Enviar mensagem no chat
- [ ] Receber resposta do Companion

---

## 🚀 Comece Agora

**Ordem recomendada:**
1. ✅ Configurar backend (15-30 min)
2. ✅ Testar API manualmente (10 min)
3. ✅ Configurar Android (10 min)
4. ✅ Testar app completo (15 min)
5. ✅ Corrigir bugs encontrados
6. ✅ Melhorar UI/UX

**Tempo estimado total:** 1-2 horas para ter tudo funcionando.

---

## 💡 Dicas

- **Use Supabase** para PostgreSQL (grátis e fácil)
- **Use Postman/Insomnia** para testar a API
- **Use Android Emulator** se não tiver dispositivo físico
- **Leia os logs** quando algo não funcionar
- **Consulte SETUP.md** para detalhes

---

## 🆘 Precisa de Ajuda?

1. Verifique os logs do backend
2. Verifique os logs do Android (Logcat)
3. Teste a API diretamente (Postman/curl)
4. Consulte `SETUP.md` para troubleshooting
5. Verifique se todas as variáveis de ambiente estão corretas

