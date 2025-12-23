# 📱 Guia de Setup - COMPANION iOS

Guia passo a passo completo para configurar o app iOS do COMPANION.

## 📋 Pré-requisitos

### 1. Hardware e Software
- **macOS** (obrigatório - não funciona no Windows/Linux)
- **Xcode 15.0 ou superior** (baixe na App Store)
- **iOS 16.0+** (deployment target)
- **Dispositivo iOS** ou **Simulador iOS**

### 2. Contas Necessárias
- **Apple Developer Account** (gratuita para desenvolvimento)
- **Backend rodando** (localhost:3001 ou servidor remoto)

---

## 🚀 PASSO 1: Instalar Xcode

### 1.1. Baixar Xcode
1. Abra a **App Store** no Mac
2. Procure por **"Xcode"**
3. Clique em **"Obter"** ou **"Instalar"**
4. Aguarde o download (é grande, ~15GB)

### 1.2. Aceitar Licença
1. Abra o **Xcode**
2. Vá em **Xcode → Settings → Accounts**
3. Adicione sua **Apple ID** (pode usar conta pessoal)
4. Aceite os termos de licença

---

## 🚀 PASSO 2: Instalar CocoaPods

### 2.1. Abrir Terminal
1. Pressione **Cmd + Espaço**
2. Digite **"Terminal"**
3. Pressione **Enter**

### 2.2. Instalar CocoaPods
No Terminal, execute:

```bash
sudo gem install cocoapods
```

Digite sua senha do Mac quando solicitado.

### 2.3. Verificar Instalação
```bash
pod --version
```

Deve mostrar a versão (ex: `1.13.0`)

---

## 🚀 PASSO 3: Configurar o Projeto

### 3.1. Navegar até a pasta iOS
No Terminal:

```bash
cd ~/Desktop/companion/ios
```

### 3.2. Instalar Dependências
```bash
pod install
```

Isso vai:
- Baixar as dependências (Alamofire, SwiftyJSON)
- Criar o arquivo `Companion.xcworkspace`

### 3.3. Abrir o Projeto
```bash
open Companion.xcworkspace
```

⚠️ **IMPORTANTE**: Sempre abra o `.xcworkspace`, NÃO o `.xcodeproj`!

---

## 🚀 PASSO 4: Configurar a URL da API

### 4.1. Localizar o Arquivo
No Xcode, no navegador de arquivos à esquerda:
1. Expanda `Companion`
2. Expanda `Services`
3. Clique em `APIService.swift`

### 4.2. Editar a URL
Encontre a linha:
```swift
private let baseURL = "http://localhost:3001/api/"
```

**Para Simulador iOS:**
```swift
private let baseURL = "http://localhost:3001/api/"
```

**Para Dispositivo Físico:**
1. Descubra o IP do seu Mac:
   ```bash
   ifconfig | grep "inet " | grep -v 127.0.0.1
   ```
2. Use o IP encontrado:
   ```swift
   private let baseURL = "http://192.168.1.XXX:3001/api/"
   ```

### 4.3. Salvar
Pressione **Cmd + S**

---

## 🚀 PASSO 5: Configurar o Projeto no Xcode

### 5.1. Selecionar o Target
1. No topo do Xcode, clique no nome do projeto (ao lado do botão Play)
2. Selecione **"Companion"**

### 5.2. Configurar Signing
1. Na aba **"Signing & Capabilities"**
2. Marque **"Automatically manage signing"**
3. Selecione seu **Team** (sua Apple ID)
4. Xcode vai gerar automaticamente um **Bundle Identifier**

### 5.3. Selecionar Dispositivo
No topo, ao lado do botão Play:
- **Para Simulador**: Selecione um iPhone (ex: "iPhone 15 Pro")
- **Para Dispositivo Físico**: Conecte seu iPhone via USB e selecione-o

---

## 🚀 PASSO 6: Executar o App

### 6.1. Build e Run
1. Pressione **Cmd + R** (ou clique no botão ▶️ Play)
2. Aguarde o build (primeira vez pode demorar)
3. O app vai abrir no simulador/dispositivo

### 6.2. Verificar Logs
Se houver erros, veja o console na parte inferior do Xcode.

---

## 🧪 PASSO 7: Testar o App

### 7.1. Fluxo de Teste
1. **Tela Inicial**: Deve mostrar login/registro
2. **Registrar**: Crie uma conta
3. **Onboarding**: Complete o fluxo
4. **Chat**: Teste enviar mensagem

### 7.2. Verificar Conexão com Backend
- Se der erro de conexão, verifique:
  - Backend está rodando?
  - URL da API está correta?
  - Firewall não está bloqueando?

---

## 🔧 Troubleshooting

### Erro: "No such module 'Alamofire'"
**Solução:**
```bash
cd ios
pod install
```
Depois, feche e reabra o Xcode.

### Erro: "Could not find module 'Companion'"
**Solução:**
1. No Xcode: **Product → Clean Build Folder** (Cmd + Shift + K)
2. **Product → Build** (Cmd + B)
3. Tente rodar novamente

### Erro de Conexão com Backend
**Solução:**
- Verifique se o backend está rodando: `http://localhost:3001`
- Para dispositivo físico, use o IP do Mac na URL
- Verifique o firewall do Mac

### Erro de Signing
**Solução:**
1. Vá em **Signing & Capabilities**
2. Mude o **Bundle Identifier** para algo único (ex: `com.seunome.companion`)
3. Marque **"Automatically manage signing"**

### Simulador não inicia
**Solução:**
1. Vá em **Xcode → Settings → Platforms**
2. Baixe o iOS Simulator mais recente
3. Reinicie o Xcode

---

## 📱 Testando em Dispositivo Físico

### 1. Conectar iPhone
1. Conecte o iPhone ao Mac via USB
2. No iPhone: **Configurações → Geral → Gerenciamento de Dispositivo**
3. Confie no computador se solicitado

### 2. Configurar no Xcode
1. Selecione seu iPhone no seletor de dispositivos
2. Xcode vai instalar o app automaticamente

### 3. Permitir App no iPhone
1. No iPhone: **Configurações → Geral → Gerenciamento de Dispositivo**
2. Confie no desenvolvedor

---

## ✅ Checklist Final

Antes de considerar concluído:

- [ ] Xcode instalado e funcionando
- [ ] CocoaPods instalado
- [ ] Dependências instaladas (`pod install`)
- [ ] Projeto aberto no Xcode (`.xcworkspace`)
- [ ] URL da API configurada corretamente
- [ ] Signing configurado
- [ ] App compila sem erros
- [ ] App roda no simulador/dispositivo
- [ ] Conecta ao backend
- [ ] Fluxo de registro/login funciona
- [ ] Onboarding funciona
- [ ] Chat funciona

---

## 🎉 Próximos Passos

Depois que tudo estiver funcionando:

1. **Personalizar UI**: Ajustar cores, fontes, layout
2. **Adicionar Avatar Visual**: Implementar renderização do avatar
3. **Melhorar UX**: Adicionar animações, feedback visual
4. **Testes**: Adicionar testes unitários
5. **Publicar**: Preparar para App Store (futuro)

---

## 📞 Precisa de Ajuda?

- **Logs do Xcode**: Veja o console na parte inferior
- **Logs do Backend**: Verifique o terminal onde o backend está rodando
- **Documentação**: Consulte `README.md` e `SETUP.md`

---

**Boa sorte com o desenvolvimento! 🚀**

