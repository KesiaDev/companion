# 🔧 Solução: "Module not specified" no Android Studio

## ❌ Problema
Erro: "Module not specified" ao tentar executar o app.

## ✅ Solução Passo a Passo

### Passo 1: Fechar a janela de configuração
1. Clique em **"Cancel"** na janela de configuração
2. Feche qualquer outra janela de erro

### Passo 2: Fechar o projeto atual
1. **File → Close Project**
2. Você vai voltar para a tela inicial do Android Studio

### Passo 3: Abrir a pasta CORRETA
⚠️ **IMPORTANTE**: Abra a pasta `android`, NÃO a pasta `companion`!

1. Na tela inicial, clique em **"Open"**
2. Navegue até: `C:\Users\User\Desktop\companion`
3. **Selecione a pasta `android`** (não a pasta `companion`)
4. Clique em **"OK"**

### Passo 4: Aguardar sincronização
- O Android Studio vai:
  - Detectar que é um projeto Android
  - Sincronizar o Gradle automaticamente
  - Reconhecer o módulo `app`

### Passo 5: Verificar se funcionou
1. No topo, ao lado do botão ▶️, deve aparecer algo como:
   - `app` ou `companion.app`
   - Um dispositivo/emulador selecionado

2. Se aparecer, está pronto para executar!

---

## 🔄 Se ainda não funcionar

### Alternativa 1: Abrir via linha de comando
No terminal PowerShell:
```powershell
cd C:\Users\User\Desktop\companion\android
start "" "C:\Program Files\Android\Android Studio\bin\studio64.exe" .
```

### Alternativa 2: Verificar estrutura do projeto
Certifique-se de que existe:
- `android/build.gradle.kts` ✅
- `android/settings.gradle.kts` ✅
- `android/app/build.gradle.kts` ✅

### Alternativa 3: Sincronizar manualmente
1. No Android Studio, pressione: **Ctrl + Shift + A**
2. Digite: `sync project with gradle files`
3. Pressione Enter

---

## ✅ Checklist Final

Antes de tentar executar:
- [ ] Projeto foi aberto na pasta `android` (não `companion`)
- [ ] Gradle sincronizou sem erros
- [ ] Módulo `app` aparece no dropdown de configuração
- [ ] Dispositivo/emulador está selecionado

---

## 🆘 Ainda com problemas?

Me diga:
1. Qual pasta você abriu no Android Studio? (`companion` ou `android`?)
2. Aparece algum erro na parte inferior do Android Studio?
3. O painel "Gradle" (lado direito) mostra algo?

