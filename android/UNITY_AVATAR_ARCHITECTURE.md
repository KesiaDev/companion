# 🎮 Arquitetura Unity Avatar - COMPANION

## 📋 Visão Geral

O avatar do Companion é renderizado **exclusivamente em Unity (URP)**, não em Jetpack Compose. O Compose apenas hospeda o container do Unity.

---

## 🏗️ Arquitetura

```
┌─────────────────────────────────────┐
│     Jetpack Compose (Android)      │
│                                     │
│  ┌───────────────────────────────┐ │
│  │   UnityContainer (Compose)    │ │
│  │                                │ │
│  │  ┌──────────────────────────┐ │ │
│  │  │   UnityPlayer (View)      │ │ │
│  │  │   - Renderiza avatar 3D   │ │ │
│  │  │   - Recebe estados        │ │ │
│  │  └──────────────────────────┘ │ │
│  └───────────────────────────────┘ │
│                                     │
│  ┌───────────────────────────────┐ │
│  │   AvatarStateManager          │ │
│  │   - Gerencia estados          │ │
│  │   - Comunica com Unity        │ │
│  └──────────────────────────────┘ │
└─────────────────────────────────────┘
           ↕️ (UnityMessage)
┌─────────────────────────────────────┐
│         Unity (URP)                │
│                                     │
│  ┌───────────────────────────────┐ │
│  │   AvatarController.cs          │ │
│  │   - Renderiza avatar 3D        │ │
│  │   - Animações faciais          │ │
│  │   - Estados emocionais         │ │
│  └──────────────────────────────┘ │
│                                     │
│  ┌───────────────────────────────┐ │
│  │   Avatar 3D (Busto)           │ │
│  │   - Modelo humano realista     │ │
│  │   - Blend Shapes (emoções)     │ │
│  └──────────────────────────────┘ │
└─────────────────────────────────────┘
```

---

## 📁 Estrutura de Arquivos

### Android (Kotlin/Compose)

```
android/app/src/main/java/com/companion/app/
├── unity/
│   ├── UnityContainer.kt          # Composable que hospeda Unity
│   ├── UnityMessageHandler.kt      # Comunicação Android ↔ Unity
│   └── AvatarStateManager.kt      # Gerencia estados do avatar
├── domain/
│   └── model/
│       └── AvatarEmotionalState.kt # Estados emocionais
└── ui/
    └── components/
        └── UnityAvatarHost.kt     # Componente Compose principal
```

### Unity (C#)

```
unity/CompanionAvatar/
├── Assets/
│   ├── Scripts/
│   │   ├── AvatarController.cs    # Controla avatar 3D
│   │   ├── UnityMessageReceiver.cs # Recebe mensagens do Android
│   │   └── EmotionalStateHandler.cs # Gerencia estados emocionais
│   ├── Models/
│   │   └── AvatarBust.fbx         # Modelo 3D do avatar (busto)
│   └── Animations/
│       ├── Neutral.anim           # Estado neutro
│       ├── Attentive.anim          # Estado atento
│       └── Smiling.anim            # Sorrindo leve
```

---

## 🎭 Estados Emocionais

### Enum de Estados

```kotlin
enum class AvatarEmotionalState {
    NEUTRAL,      // Neutro - expressão calma
    ATTENTIVE,    // Atento - olhos mais abertos, leve inclinação
    SMILING       // Sorrindo leve - sorriso sutil e acolhedor
}
```

### Transições

- **Suaves**: Todas as transições entre estados são animadas (1-2s)
- **Naturais**: Sem movimentos bruscos
- **Contextuais**: Estados mudam baseados na conversa

---

## 🔌 Comunicação Android ↔ Unity

### Método: UnityMessage

```kotlin
// Android → Unity
UnityPlayer.UnitySendMessage(
    "AvatarController",      // GameObject name
    "SetEmotionalState",     // Method name
    "NEUTRAL"                // Parameter (JSON string)
)
```

### Unity → Android (se necessário)

```csharp
// Unity → Android
AndroidJavaClass unityPlayer = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
AndroidJavaObject currentActivity = unityPlayer.GetStatic<AndroidJavaObject>("currentActivity");
currentActivity.Call("onAvatarStateChanged", stateJson);
```

---

## 📦 Dependências Android

### build.gradle.kts

```kotlin
dependencies {
    // Unity as a Library
    implementation(files("libs/unity-classes.jar"))
    implementation("com.unity3d.player:unity-player:3.0")
    
    // Unity Android Support
    implementation("com.unity3d.player:unity-android-support:1.0")
}
```

---

## 🚀 Implementação

### 1. UnityContainer.kt

Composable que hospeda a UnityView:

```kotlin
@Composable
fun UnityAvatarContainer(
    emotionalState: AvatarEmotionalState,
    modifier: Modifier = Modifier
) {
    AndroidView(
        factory = { context ->
            UnityPlayer(context).apply {
                // Configurar Unity
            }
        },
        modifier = modifier
    )
}
```

### 2. AvatarStateManager.kt

Gerencia estados e comunica com Unity:

```kotlin
class AvatarStateManager {
    fun setEmotionalState(state: AvatarEmotionalState) {
        UnityPlayer.UnitySendMessage(
            "AvatarController",
            "SetEmotionalState",
            state.name
        )
    }
}
```

### 3. UnityAvatarHost.kt

Componente principal Compose:

```kotlin
@Composable
fun UnityAvatarHost(
    emotionalState: AvatarEmotionalState = AvatarEmotionalState.NEUTRAL,
    modifier: Modifier = Modifier
) {
    UnityAvatarContainer(
        emotionalState = emotionalState,
        modifier = modifier
    )
}
```

---

## 📝 Próximos Passos

1. **Criar projeto Unity**
   - Configurar URP
   - Importar modelo de avatar 3D (busto)
   - Configurar Blend Shapes para emoções

2. **Exportar como Library**
   - Build Unity as a Library
   - Gerar `.aar` ou integrar diretamente

3. **Integrar no Android**
   - Adicionar Unity classes ao projeto
   - Implementar UnityContainer
   - Conectar estados emocionais

4. **Testar integração**
   - Verificar renderização
   - Testar mudanças de estado
   - Validar performance

---

## ⚠️ Notas Importantes

- **NÃO usar Canvas/Shapes** para avatar em Compose
- **Unity é responsável** por toda renderização 3D
- **Compose apenas hospeda** o container Unity
- **Estados são enviados** via UnityMessage
- **Avatar é placeholder** inicial (busto humano realista)

---

**Arquitetura preparada para Unity! 🎮**

