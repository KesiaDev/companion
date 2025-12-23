# 🚀 Quick Start - Android COMPANION

Guia rápido para executar o app Android pela primeira vez.

## ✅ Passo 1: Configurar URL da API

### 1.1. Localizar o arquivo
No Android Studio:
1. No explorador de arquivos (esquerda), expanda: `app → src → main → java → com → companion → app → data → remote`
2. Abra: `RetrofitClient.kt`

### 1.2. Editar a URL
Encontre a linha:
```kotlin
private const val BASE_URL = "http://10.0.2.2:3000/api/"
```

**Para Emulador Android:**
```kotlin
private const val BASE_URL = "http://10.0.2.2:3001/api/"
```
(Note: porta 3001, não 3000)

**Para Dispositivo Físico:**
1. Descubra o IP do seu PC:
   - Windows: Abra PowerShell e execute: `ipconfig`
   - Procure por "IPv4 Address" (ex: 192.168.1.100)
2. Use o IP encontrado:
```kotlin
private const val BASE_URL = "http://192.168.1.XXX:3001/api/"
```

### 1.3. Salvar
Pressione **Ctrl + S**

---

## ✅ Passo 2: Sincronizar Gradle

### 2.1. Sincronizar
1. No Android Studio, clique em: **File → Sync Project with Gradle Files**
2. Ou clique no ícone de elefante no topo
3. Aguarde a sincronização terminar

### 2.2. Verificar erros
- Se houver erros, veja a aba **"Build"** na parte inferior
- Corrija conforme necessário

---

## ✅ Passo 3: Verificar Backend

### 3.1. Backend deve estar rodando
No terminal onde o backend está:
```powershell
cd C:\Users\User\Desktop\companion\backend
npm run dev
```

Deve mostrar:
```
✓ Ready
- Local: http://localhost:3001
```

### 3.2. Testar API
No navegador, acesse:
```
http://localhost:3001/api/auth/register
```

Se aparecer erro de método, está funcionando! ✅

---

## ✅ Passo 4: Executar o App

### 4.1. Selecionar dispositivo
No topo do Android Studio:
- **Para Emulador**: Clique no dropdown e selecione um dispositivo (ex: "Pixel 5 API 34")
- **Para Dispositivo Físico**: Conecte seu Android via USB e ative "Depuração USB"

### 4.2. Executar
1. Clique no botão **▶️ Run** (ou pressione **Shift + F10**)
2. Aguarde o build (primeira vez pode demorar 5-10 minutos)
3. O app vai abrir no dispositivo/emulador

---

## 🧪 Passo 5: Testar o App

### 5.1. Fluxo de Teste
1. **Splash Screen** → Aguarde 2 segundos
2. **Verificação de Idade** → Clique "Sim" e "Continuar"
3. **Login/Registro** → Crie uma conta ou faça login
4. **Onboarding** → Complete o fluxo:
   - Escolha tipo de companhia
   - Escolha tom de conversa
   - Crie o avatar
5. **Chat** → Envie uma mensagem

### 5.2. Verificar Logs
Se algo não funcionar:
- Veja a aba **"Logcat"** na parte inferior
- Filtre por "companion" ou "error"

---

## 🔧 Problemas Comuns

### Erro: "Failed to connect to /10.0.2.2:3001"
**Solução:**
- Verifique se o backend está rodando
- Verifique se a porta está correta (3001)
- Para dispositivo físico, use o IP do PC

### Erro: "Gradle sync failed"
**Solução:**
1. **File → Invalidate Caches / Restart**
2. Selecione **"Invalidate and Restart"**
3. Aguarde o Android Studio reiniciar
4. Tente sincronizar novamente

### Erro: "SDK not found"
**Solução:**
1. **File → Settings → Appearance & Behavior → System Settings → Android SDK**
2. Instale o SDK necessário
3. Sincronize novamente

### App não conecta ao backend
**Solução:**
- Verifique a URL no `RetrofitClient.kt`
- Verifique se o backend está rodando
- Para dispositivo físico, verifique se está na mesma rede WiFi
- Desative firewall temporariamente para testar

### Build muito lento
**Solução:**
- Primeira vez sempre demora (baixa dependências)
- Feche outros programas
- Aumente memória do Android Studio (se possível)

---

## ✅ Checklist Rápido

Antes de executar, verifique:

- [ ] Backend está rodando (porta 3001)
- [ ] URL da API configurada no `RetrofitClient.kt`
- [ ] Gradle sincronizado sem erros
- [ ] Dispositivo/Emulador selecionado
- [ ] Build completo sem erros

---

## 🎉 Próximos Passos

Depois que o app estiver rodando:

1. **Teste todas as telas**
2. **Teste o fluxo completo**
3. **Verifique se conecta ao backend**
4. **Teste enviar mensagem no chat**
5. **Reporte bugs encontrados**

---

## 📞 Precisa de Ajuda?

- **Logs**: Veja a aba "Logcat"
- **Erros de Build**: Veja a aba "Build"
- **Conexão**: Teste a API no navegador primeiro
- **Documentação**: Consulte `SETUP.md` e `README.md`

**Boa sorte! 🚀**

