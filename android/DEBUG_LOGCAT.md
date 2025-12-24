# 🔍 Como Ver Logs no Logcat - Android Studio

## Problema: "All logs entries are hidden by the filter"

Se você vê essa mensagem, o filtro está muito restritivo.

## ✅ Solução Rápida

### 1. Remover o Filtro
No Logcat:
- Clique no campo de filtro (onde está "ChatViewModel")
- Apague o texto ou clique no "X" ao lado
- Agora você verá TODOS os logs

### 2. Usar Filtros Úteis

**Para ver erros:**
- Filtro: `level:error`
- Ou: `tag:AndroidRuntime`

**Para ver logs do app:**
- Filtro: `package:com.companion.app`
- Ou: `tag:ChatViewModel | tag:AuthViewModel`

**Para ver requisições HTTP:**
- Filtro: `tag:OkHttp`

**Para ver tudo relacionado ao Companion:**
- Filtro: `companion`

### 3. Verificar se o App Está Rodando

Se não há logs, o app pode não estar executando:
1. Verifique se o app está aberto no emulador
2. Tente enviar uma mensagem no chat
3. Os logs devem aparecer automaticamente

### 4. Limpar e Recarregar

1. Clique no ícone de **lixeira** (limpar logs)
2. Clique no ícone de **refresh** (atualizar)
3. Tente enviar uma mensagem novamente

---

## 📋 Filtros Recomendados para Debug

### Ver Tudo do App
```
package:com.companion.app
```

### Ver Apenas Erros
```
level:error package:com.companion.app
```

### Ver Logs de Chat
```
ChatViewModel
```

### Ver Requisições HTTP
```
OkHttp
```

### Ver Autenticação
```
AuthViewModel
```

---

## 🐛 Logs que Você Deve Procurar

Quando enviar uma mensagem no chat, você deve ver:

```
D/ChatViewModel: Enviando mensagem: [sua mensagem]
D/ChatViewModel: Token presente: [primeiros caracteres]...
D/ChatViewModel: Resposta recebida: [código HTTP]
```

Se houver erro:
```
E/ChatViewModel: Erro de conexão: [tipo de erro]
```

---

## ⚠️ Se Ainda Não Ver Logs

1. **Verifique o dispositivo selecionado:**
   - No dropdown do Logcat, confirme que está selecionado o emulador/dispositivo correto

2. **Reinicie o Logcat:**
   - Clique no ícone de refresh
   - Ou feche e abra a aba Logcat novamente

3. **Verifique se o app está rodando:**
   - O app precisa estar aberto e ativo
   - Tente interagir com o app (enviar mensagem)

4. **Verifique o nível de log:**
   - No dropdown do Logcat, selecione "Verbose" ou "Debug"
   - Logs de nível "Info" ou superior podem não mostrar tudo

