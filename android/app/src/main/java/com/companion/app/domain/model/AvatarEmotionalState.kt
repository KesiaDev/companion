package com.companion.app.domain.model

/**
 * Estados emocionais do avatar
 * Controlados via Unity
 */
enum class AvatarEmotionalState {
    /**
     * Neutro - expressão calma e serena
     * Estado padrão quando não há interação
     */
    NEUTRAL,
    
    /**
     * Atento - olhos mais abertos, leve inclinação da cabeça
     * Quando está "ouvindo" o usuário
     */
    ATTENTIVE,
    
    /**
     * Sorrindo leve - sorriso sutil e acolhedor
     * Quando há interação positiva ou resposta empática
     */
    SMILING
}

/**
 * Converte string para estado emocional
 */
fun String.toEmotionalState(): AvatarEmotionalState {
    return when (this.uppercase()) {
        "NEUTRAL" -> AvatarEmotionalState.NEUTRAL
        "ATTENTIVE" -> AvatarEmotionalState.ATTENTIVE
        "SMILING" -> AvatarEmotionalState.SMILING
        else -> AvatarEmotionalState.NEUTRAL
    }
}

