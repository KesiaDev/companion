# 🔌 Guia de Integração Unity - COMPANION

## 📋 Visão Geral

Este guia explica como integrar o avatar Unity no projeto Android usando **Unity as a Library**.

---

## ✅ Pré-requisitos

1. **Unity 2021.3 LTS ou superior**
2. **URP (Universal Render Pipeline)** configurado
3. **Projeto Unity exportado** como Library
4. **Android Studio** com projeto Android

---

## 📦 Passo 1: Exportar Unity como Library

### No Unity:

1. **File → Build Settings**
2. Selecionar **Android**
3. **Build System**: Gradle
4. Marcar **Export Project**
5. Clicar **Build**
6. Escolher pasta de destino (ex: `unity-build/`)

### Resultado:

```
unity-build/
├── unityLibrary/          # Módulo Unity
├── launcher/             # (Opcional)
└── build.gradle          # Gradle do Unity
```

---

## 📦 Passo 2: Integrar no Android

### 2.1. Copiar Unity Library

Copiar `unityLibrary/` para:
```
android/
└── unityLibrary/         # Módulo Unity
```

### 2.2. Atualizar settings.gradle.kts

```kotlin
include(":app")
include(":unityLibrary")  // Adicionar módulo Unity
```

### 2.3. Atualizar app/build.gradle.kts

```kotlin
dependencies {
    // ... outras dependências
    
    // Unity Library
    implementation(project(":unityLibrary"))
}
```

### 2.4. Configurar ProGuard (se necessário)

Adicionar em `proguard-rules.pro`:
```
-keep class com.unity3d.** { *; }
-keep class com.unity.** { *; }
-dontwarn com.unity3d.**
```

---

## 📦 Passo 3: Implementar UnityContainer

### 3.1. Atualizar UnityAvatarHost.kt

```kotlin
@Composable
fun UnityAvatarHost(
    emotionalState: AvatarEmotionalState,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val stateManager = remember { AvatarStateManager() }
    
    AndroidView(
        factory = { ctx ->
            UnityPlayer(ctx).apply {
                // Unity está pronto
                stateManager.initialize(this)
            }
        },
        modifier = modifier,
        update = { view ->
            // Atualizar quando estado mudar
            stateManager.setEmotionalState(emotionalState)
        }
    )
    
    DisposableEffect(Unit) {
        onDispose {
            stateManager.cleanup()
        }
    }
}
```

---

## 📦 Passo 4: Testar Integração

### 4.1. Verificar Build

```bash
./gradlew assembleDebug
```

### 4.2. Executar App

- Unity deve inicializar
- Avatar 3D deve aparecer
- Estados emocionais devem funcionar

---

## 🔧 Troubleshooting

### Erro: "Unity classes not found"

**Solução:**
- Verificar se `unityLibrary` está incluído em `settings.gradle.kts`
- Verificar se dependência está em `app/build.gradle.kts`
- Fazer **Sync Project with Gradle Files**

### Erro: "UnityPlayer not found"

**Solução:**
- Verificar se Unity foi exportado corretamente
- Verificar se `unityLibrary` está completo
- Limpar e rebuild: **Build → Clean Project**

### Avatar não aparece

**Solução:**
- Verificar logs do Unity (Logcat)
- Verificar se GameObject "AvatarController" existe
- Verificar se método "SetEmotionalState" está implementado

---

## 📝 Checklist de Integração

- [ ] Unity exportado como Library
- [ ] `unityLibrary/` copiado para `android/`
- [ ] `settings.gradle.kts` atualizado
- [ ] `app/build.gradle.kts` atualizado
- [ ] `UnityAvatarHost` implementado
- [ ] `AvatarStateManager` funcionando
- [ ] Estados emocionais testados
- [ ] Performance validada

---

## 🚀 Próximos Passos

1. **Criar projeto Unity** com avatar 3D
2. **Implementar AvatarController** em C#
3. **Configurar estados emocionais** com animações
4. **Exportar e integrar** no Android
5. **Testar e otimizar**

---

**Guia de integração pronto! 🔌**

