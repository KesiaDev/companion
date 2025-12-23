# 📘 Guia Passo a Passo - Configuração do COMPANION

## 🎯 Objetivo
Configurar a chave da OpenAI e iniciar o servidor backend.

---

## 📝 PASSO 1: Obter a Chave da OpenAI

### 1.1. Acessar o site da OpenAI
1. Abra seu navegador (Chrome, Edge, Firefox, etc.)
2. Acesse: **https://platform.openai.com/api-keys**
3. **Faça login** na sua conta OpenAI (ou crie uma conta se não tiver)

### 1.2. Criar uma nova chave
1. Na página de API Keys, clique no botão **"+ Create new secret key"** (ou similar)
2. Dê um nome para a chave (ex: "Companion App")
3. Clique em **"Create secret key"**
4. **IMPORTANTE**: Copie a chave imediatamente! Ela começa com `sk-` e tem vários caracteres
   - Exemplo: `sk-proj-abc123xyz...`
   - ⚠️ **Você não poderá ver essa chave novamente!**

### 1.3. Salvar a chave temporariamente
- Cole em um bloco de notas ou documento temporário
- Você vai precisar dela no próximo passo

---

## 📝 PASSO 2: Editar o arquivo .env

### 2.1. Abrir o arquivo .env
No terminal PowerShell (onde você está), execute:

```powershell
cd C:\Users\User\Desktop\companion\backend
notepad .env
```

Ou, se preferir usar o VS Code:
```powershell
code .env
```

### 2.2. Localizar a linha OPENAI_API_KEY
Você verá algo assim:
```
OPENAI_API_KEY="sua-chave-openai-aqui"
```

### 2.3. Substituir o valor
1. **Selecione** o texto entre as aspas: `sua-chave-openai-aqui`
2. **Delete** esse texto
3. **Cole** a chave que você copiou da OpenAI (a que começa com `sk-`)
4. Deve ficar assim:
   ```
   OPENAI_API_KEY="sk-proj-abc123xyz..."
   ```
   ⚠️ **Mantenha as aspas!**

### 2.4. Salvar o arquivo
- Pressione **Ctrl + S** (ou File → Save)
- Feche o arquivo

---

## 📝 PASSO 3: Configurar JWT_SECRET (Opcional mas Recomendado)

### 3.1. Gerar uma chave segura
No terminal PowerShell, execute:

```powershell
# Opção 1: Gerar string aleatória (mais seguro)
-join ((65..90) + (97..122) + (48..57) | Get-Random -Count 32 | ForEach-Object {[char]$_})

# Opção 2: Usar uma string simples (apenas para testes)
# Pode usar: "meu-secret-jwt-companion-2024"
```

### 3.2. Editar o arquivo .env novamente
```powershell
notepad .env
```

### 3.3. Localizar JWT_SECRET
Encontre a linha:
```
JWT_SECRET="seu-secret-jwt-super-seguro-aqui-mude-isso"
```

### 3.4. Substituir o valor
1. **Selecione** o texto entre as aspas
2. **Cole** a chave gerada (ou use uma string simples para testes)
3. Exemplo:
   ```
   JWT_SECRET="AbC123XyZ456MeN789OpQ012RsT345"
   ```
   ou para testes:
   ```
   JWT_SECRET="companion-secret-key-2024"
   ```

### 3.5. Salvar o arquivo
- **Ctrl + S**
- Feche o arquivo

---

## 📝 PASSO 4: Verificar o arquivo .env

### 4.1. Ver o conteúdo do arquivo
No terminal, execute:

```powershell
Get-Content .env
```

### 4.2. Verificar se está correto
Você deve ver algo assim:
```
DATABASE_URL="file:./dev.db"
JWT_SECRET="sua-chave-jwt-aqui"
JWT_EXPIRES_IN="7d"
OPENAI_API_KEY="sk-proj-sua-chave-openai-aqui"
NODE_ENV="development"
PORT=3000
```

✅ **Verifique:**
- `OPENAI_API_KEY` tem uma chave que começa com `sk-`
- `JWT_SECRET` não está mais com o texto padrão
- Todas as linhas estão corretas

---

## 📝 PASSO 5: Iniciar o Servidor

### 5.1. Certificar-se de estar na pasta correta
```powershell
cd C:\Users\User\Desktop\companion\backend
```

### 5.2. Iniciar o servidor
```powershell
npm run dev
```

### 5.3. Aguardar a inicialização
Você verá mensagens como:
```
> companion-backend@1.0.0 dev
> next dev

  ▲ Next.js 14.x.x
  - Local:        http://localhost:3000
  - Ready in Xs
```

✅ **Se você ver "Ready" ou "Local: http://localhost:3000", o servidor está funcionando!**

### 5.4. Manter o terminal aberto
- ⚠️ **NÃO feche o terminal** enquanto o servidor estiver rodando
- O servidor precisa ficar rodando para receber requisições
- Para parar o servidor, pressione **Ctrl + C**

---

## 🧪 PASSO 6: Testar o Servidor

### 6.1. Abrir outro terminal (ou usar o navegador)
Mantenha o servidor rodando e abra **outro terminal** ou use o **navegador**.

### 6.2. Testar no navegador
1. Abra o navegador
2. Acesse: **http://localhost:3000**
3. Você pode ver uma página do Next.js ou uma mensagem de erro (isso é normal se não houver página inicial)

### 6.3. Testar a API (opcional)
No **novo terminal**, execute:

```powershell
# Testar registro de usuário
curl http://localhost:3000/api/auth/register -Method POST -Headers @{"Content-Type"="application/json"} -Body '{"email":"teste@teste.com","password":"senha123"}'
```

Se funcionar, você verá uma resposta JSON com `user` e `token`.

---

## ✅ Checklist Final

Antes de considerar concluído, verifique:

- [ ] Tenho uma conta na OpenAI
- [ ] Criei uma API Key na OpenAI
- [ ] Copiei a chave (começa com `sk-`)
- [ ] Editei o arquivo `.env` e coloquei a chave da OpenAI
- [ ] Configurei o `JWT_SECRET` (ou deixei o padrão para testes)
- [ ] Salvei o arquivo `.env`
- [ ] Executei `npm run dev`
- [ ] O servidor iniciou sem erros
- [ ] Vejo a mensagem "Ready" ou "Local: http://localhost:3000"

---

## 🆘 Problemas Comuns

### Erro: "OPENAI_API_KEY is not defined"
- **Solução**: Verifique se você salvou o arquivo `.env` corretamente
- **Solução**: Verifique se a chave está entre aspas: `"sk-..."`

### Erro: "Invalid API Key"
- **Solução**: Verifique se copiou a chave completa (ela é longa)
- **Solução**: Verifique se não há espaços antes ou depois da chave

### Servidor não inicia
- **Solução**: Verifique se está na pasta `backend`: `cd C:\Users\User\Desktop\companion\backend`
- **Solução**: Verifique se instalou as dependências: `npm install`

### Porta 3000 já está em uso
- **Solução**: Pare outros servidores na porta 3000
- **Solução**: Ou mude a porta no `.env`: `PORT=3001`

---

## 🎉 Próximo Passo

Depois que o servidor estiver rodando, você pode:
1. **Testar o app Android** (conectando ao servidor)
2. **Testar as rotas da API** manualmente
3. **Começar a desenvolver** novas funcionalidades

---

## 📞 Precisa de Ajuda?

Se algo não funcionar:
1. Verifique os logs no terminal onde o servidor está rodando
2. Verifique se o arquivo `.env` está correto
3. Verifique se a chave da OpenAI é válida
4. Consulte `SETUP.md` para mais detalhes

