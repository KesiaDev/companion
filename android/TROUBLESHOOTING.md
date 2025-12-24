# 🔧 Troubleshooting - COMPANION Android

Guia para resolver problemas comuns no app Android.

## ❌ Erro: "Desculpe, estou tendo dificuldades técnicas"

Este erro aparece quando o app não consegue se comunicar com o backend. Siga estes passos:

### 1. Verificar se o Backend está rodando

Abra um terminal PowerShell e execute:

```powershell
cd C:\Users\User\Desktop\companion\backend
npm run dev
```

**Deve aparecer:**
```
✓ Ready
- Local: http://localhost:3001
```

Se não aparecer, o backend não está rodando. Corrija os erros antes de continuar.

---

### 2. Verificar a URL da API no App

No Android Studio:
1. Abra: `app → src → main → java → com → companion → app → data → remote → RetrofitClient.kt`
2. Verifique a linha:
   ```kotlin
   private const val BASE_URL = "http://10.0.2.2:3001/api/"
   ```

**Para Emulador Android:**
- Deve ser: `http://10.0.2.2:3001/api/` ✅

**Para Dispositivo Físico:**
- Descubra o IP do seu PC:
  ```powershell
  ipconfig
  ```
- Procure por "IPv4 Address" (ex: `192.168.1.100`)
- Use: `http://192.168.1.XXX:3001/api/`

---

### 3. Verificar Logs do App

No Android Studio:
1. Abra a aba **"Logcat"** (parte inferior)
2. Filtre por: `ChatViewModel`
3. Procure por mensagens de erro

**Erros comuns:**
- `UnknownHostException` → Backend não encontrado (verifique URL)
- `ConnectException` → Não conseguiu conectar (verifique se backend está rodando)
- `401 Unauthorized` → Token inválido (faça login novamente)
- `404 Not Found` → Rota não encontrada (verifique URL)

---

### 4. Verificar Token de Autenticação

O app precisa estar logado para usar o chat.

**Sintomas:**
- Mensagem de erro genérica
- Log mostra "Token vazio ou ausente"

**Solução:**
1. Saia do app completamente
2. Abra novamente
3. Faça login novamente
4. Tente enviar uma mensagem

---

### 5. Verificar Firewall/Redes

**Para Dispositivo Físico:**
- PC e dispositivo devem estar na mesma rede WiFi
- Firewall do Windows pode estar bloqueando
- Teste desativar firewall temporariamente

**Para Emulador:**
- Não precisa de configuração especial
- Use `10.0.2.2` que é o alias do localhost do emulador

---

### 6. Testar Backend Manualmente

Abra o navegador e teste:

```
http://localhost:3001/api/auth/register
```

Se aparecer erro de método (POST required), o backend está funcionando! ✅

---

## 📋 Checklist de Diagnóstico

Antes de reportar um problema, verifique:

- [ ] Backend está rodando na porta 3001
- [ ] URL da API está correta no `RetrofitClient.kt`
- [ ] App está logado (token presente)
- [ ] Logcat mostra erros específicos
- [ ] Backend responde no navegador
- [ ] Firewall não está bloqueando (dispositivo físico)
- [ ] PC e dispositivo na mesma rede (dispositivo físico)

---

## 🆘 Ainda com Problemas?

Se nada funcionar:

1. **Veja os logs completos:**
   - Logcat no Android Studio
   - Terminal do backend (npm run dev)

2. **Teste a API diretamente:**
   - Use Postman ou curl
   - Teste: `POST http://localhost:3001/api/companion/chat`

3. **Reinicie tudo:**
   - Feche o app completamente
   - Pare o backend (Ctrl+C)
   - Inicie o backend novamente
   - Abra o app novamente

---

## 📝 Logs Úteis

O app agora mostra logs detalhados no Logcat:

- `ChatViewModel`: Logs de envio de mensagens
- `RetrofitClient`: Logs de requisições HTTP (se habilitado)
- `AuthViewModel`: Logs de autenticação

Filtre por essas tags para ver o que está acontecendo.

