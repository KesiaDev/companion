# 🎮 Unity Avatar Project - COMPANION

## 📋 Visão Geral

Projeto Unity para renderização do avatar 3D do Companion usando **Universal Render Pipeline (URP)**.

---

## 🎯 Objetivos

1. **Renderizar avatar 3D realista** (busto humano)
2. **Responder a estados emocionais** (neutro, atento, sorrindo)
3. **Integrar com Android** via Unity as a Library
4. **Performance otimizada** para mobile

---

## 📁 Estrutura do Projeto

```
CompanionAvatar/
├── Assets/
│   ├── Scripts/
│   │   ├── AvatarController.cs          # Controla avatar e animações
│   │   ├── UnityMessageReceiver.cs      # Recebe mensagens do Android
│   │   └── EmotionalStateHandler.cs     # Gerencia estados emocionais
│   ├── Models/
│   │   └── AvatarBust.fbx               # Modelo 3D (busto)
│   ├── Materials/
│   │   └── AvatarMaterial.mat           # Material URP
│   ├── Animations/
│   │   ├── Neutral.anim                 # Estado neutro
│   │   ├── Attentive.anim                # Estado atento
│   │   └── Smiling.anim                  # Sorrindo leve
│   └── Prefabs/
│       └── CompanionAvatar.prefab       # Prefab do avatar
├── ProjectSettings/
│   └── (Configurações URP)
└── Packages/
    └── (Dependências)
```

---

## 🎭 Estados Emocionais

### 1. NEUTRAL (Neutro)
- Expressão calma e serena
- Olhos normais
- Boca neutra
- Postura relaxada

### 2. ATTENTIVE (Atento)
- Olhos mais abertos
- Leve inclinação da cabeça
- Expressão focada
- Microanimações sutis

### 3. SMILING (Sorrindo leve)
- Sorriso sutil e acolhedor
- Olhos levemente fechados
- Expressão positiva
- Calor humano

---

## 🔌 Integração com Android

### Receber Mensagens do Android

```csharp
public class UnityMessageReceiver : MonoBehaviour
{
    public void SetEmotionalState(string stateName)
    {
        // Converter string para enum
        EmotionalState state = ParseState(stateName);
        
        // Aplicar estado ao avatar
        AvatarController.Instance.SetState(state);
    }
    
    public void UpdateAvatar(string jsonData)
    {
        // Atualizar aparência do avatar
        // (quando dados de customização estiverem prontos)
    }
}
```

### Enviar Mensagens para Android (se necessário)

```csharp
public void SendToAndroid(string message)
{
    AndroidJavaClass unityPlayer = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
    AndroidJavaObject currentActivity = unityPlayer.GetStatic<AndroidJavaObject>("currentActivity");
    currentActivity.Call("onUnityMessage", message);
}
```

---

## 🎨 Avatar 3D

### Especificações

- **Tipo**: Busto humano (cabeça + ombros)
- **Estilo**: Realista, não estilizado
- **Qualidade**: Otimizado para mobile
- **Blend Shapes**: Para expressões faciais
- **Texturas**: PBR (Physically Based Rendering)

### Customização (Futuro)

O avatar deve suportar:
- Tom de pele
- Tipo de cabelo
- Formato do rosto
- Estilo de roupa

---

## ⚙️ Configuração URP

1. **Pipeline Asset**: URP Asset configurado
2. **Render Features**: 
   - SSAO (opcional)
   - Bloom suave (opcional)
3. **Quality Settings**: Otimizado para mobile
4. **Build Settings**: Android platform

---

## 📦 Exportação

### Unity as a Library

1. **File → Build Settings**
2. Selecionar **Android**
3. **Build System**: Gradle
4. **Export Project**: ✅ Habilitado
5. **Build**

### Resultado

- Pasta `unityLibrary/` será gerada
- Integrar no projeto Android
- Adicionar dependências necessárias

---

## 🚀 Próximos Passos

1. **Criar projeto Unity**
   - Configurar URP
   - Importar modelo de avatar (placeholder inicial)

2. **Implementar AvatarController**
   - Sistema de estados
   - Animações faciais
   - Transições suaves

3. **Configurar comunicação**
   - UnityMessageReceiver
   - Testar integração com Android

4. **Otimização**
   - Performance mobile
   - Redução de polígonos
   - Texturas otimizadas

---

## 📝 Notas

- **URP obrigatório**: Não usar Built-in Render Pipeline
- **Mobile-first**: Otimizar para performance
- **Placeholder inicial**: Avatar humano genérico realista
- **Futuro**: Customização baseada em dados do usuário

---

**Projeto Unity preparado para integração! 🎮**

