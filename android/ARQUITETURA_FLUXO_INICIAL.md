# 🏗️ Arquitetura do Fluxo Inicial - COMPANION

## 📋 Visão Geral

Este documento descreve a arquitetura completa do fluxo inicial do aplicativo COMPANION, implementada seguindo os princípios de **MVVM**, **Clean Architecture** e **código escalável**.

---

## 🎯 Fluxo Completo Implementado

### 1. **Criação de Avatar** ✅
- Tela visual moderna e premium
- Preview em tempo real
- Validação de campos
- Persistência em `AvatarState`

### 2. **Primeira Conversa** ✅
- Tela de boas-vindas com animações
- Avatar com presença visual
- Integração com IA mock
- Preparado para IA real

### 3. **IA Mock (CompanionBrain)** ✅
- Camada separada e testável
- Respostas empáticas pré-definidas
- Delay humano simulado
- Preparado para substituição por IA real

### 4. **Memória Emocional Básica** ✅
- Estrutura de dados completa
- Integração com CompanionBrain
- Evolução conforme conversas
- Não invasiva, não julgadora

### 5. **Comunidade (Placeholder)** ✅
- Estrutura de dados preparada
- Tela placeholder acolhedora
- Modelos prontos para implementação futura

---

## 📁 Estrutura de Arquivos

```
android/app/src/main/java/com/companion/app/
├── domain/
│   ├── model/
│   │   ├── AvatarState.kt          # Estado do avatar
│   │   ├── EmotionalMemory.kt      # Memória emocional
│   │   └── CommunityModels.kt       # Modelos de comunidade
│   └── brain/
│       └── CompanionBrain.kt        # IA mock (preparado para real)
├── data/
│   └── local/
│       ├── AvatarRepository.kt     # Repositório de avatar
│       └── MemoryRepository.kt    # Repositório de memória
├── ui/
│   ├── screens/
│   │   ├── AvatarCreationScreen.kt      # Tela de criação
│   │   ├── FirstConversationScreen.kt    # Primeira conversa
│   │   └── CommunityPlaceholderScreen.kt # Placeholder comunidade
│   ├── viewmodel/
│   │   ├── AvatarCreationViewModel.kt    # VM criação avatar
│   │   └── FirstConversationViewModel.kt  # VM primeira conversa
│   └── navigation/
│       └── NavGraph.kt                   # Navegação completa
└── di/
    └── AppModule.kt                       # Injeção de dependências
```

---

## 🔄 Fluxo de Navegação

```
Splash
  ↓
Age Verification
  ↓
Login/Register
  ↓
Companion Type Selection
  ↓
Conversation Tone Selection
  ↓
Avatar Creation ← [Salva AvatarState]
  ↓
First Conversation ← [Inicializa Memória Emocional]
  ↓
Chat (Conversa Principal)
  ↓
Community (Placeholder)
```

---

## 🧠 Arquitetura de Dados

### **AvatarState**
```kotlin
data class AvatarState(
    val name: String,
    val pronouns: String,
    val style: String,
    val bodyType: String,
    val faceType: String,
    val hair: String,
    val skinTone: String
)
```

### **EmotionalMemory**
```kotlin
data class EmotionalMemory(
    val userName: String,
    val avatarName: String,
    val companionType: String,
    val recurringThemes: List<String>,
    val recentMood: String?,
    val conversationCount: Int,
    val lastInteraction: Long
)
```

### **CompanionBrain**
- **Responsabilidade**: Processar mensagens e gerar respostas
- **Entrada**: Mensagem do usuário
- **Saída**: Resposta empática
- **Delay**: 1-2 segundos (simulação humana)
- **Preparado para**: Substituição por IA real (OpenAI, etc.)

---

## 🎨 Decisões Técnicas

### **1. Separação de Camadas**
- **Domain**: Lógica de negócio pura (CompanionBrain, Models)
- **Data**: Repositórios e persistência
- **UI**: Telas, ViewModels, Navegação

### **2. Estado Centralizado**
- `AvatarRepository`: Gerencia estado do avatar
- `MemoryRepository`: Gerencia memória emocional
- ViewModels: Orquestram estado e lógica de UI

### **3. IA Mock Preparada para Real**
- Interface clara em `CompanionBrain`
- Fácil substituição por chamada de API
- Mantém mesma interface para UI

### **4. Memória Não Invasiva**
- Não julga sentimentos
- Não pressiona usuário
- Evolui naturalmente
- Respeita privacidade

---

## 🔌 Integrações Futuras

### **Preparado para:**
1. **Avatar 3D (Unity)**
   - `AvatarState` pode ser convertido para parâmetros Unity
   - Preview visual pode ser substituído por renderização 3D

2. **IA Real (OpenAI)**
   - `CompanionBrain` pode ser substituído por chamada de API
   - Mantém mesma interface
   - Memória emocional pode ser enviada como contexto

3. **Voz**
   - Estrutura preparada para TTS/STT
   - Placeholder no campo de texto

4. **Comunidade Real**
   - Modelos de dados prontos
   - Tela placeholder pode ser substituída
   - Estrutura escalável

---

## 📝 Padrões Seguidos

✅ **MVVM**: Separação clara de responsabilidades  
✅ **Clean Architecture**: Camadas bem definidas  
✅ **StateFlow**: Estado reativo e observável  
✅ **Coroutines**: Operações assíncronas  
✅ **Compose**: UI declarativa e moderna  
✅ **Repository Pattern**: Abstração de dados  
✅ **Dependency Injection**: Preparado para Koin  

---

## 🚀 Próximos Passos

1. **Integração com Backend**
   - Substituir repositórios locais por chamadas de API
   - Sincronizar avatar e memória

2. **IA Real**
   - Substituir `CompanionBrain` por chamada OpenAI
   - Manter mesma interface

3. **Persistência Local**
   - Adicionar Room Database ou DataStore
   - Cache de memória emocional

4. **Comunidade**
   - Implementar chat em tempo real
   - Sistema de salas e moderação

---

## 📚 Referências

- **Jetpack Compose**: UI declarativa
- **MVVM**: Padrão arquitetural
- **Clean Architecture**: Separação de camadas
- **StateFlow**: Estado reativo
- **Coroutines**: Concorrência

---

**Arquitetura implementada e pronta para evolução! 🎉**

