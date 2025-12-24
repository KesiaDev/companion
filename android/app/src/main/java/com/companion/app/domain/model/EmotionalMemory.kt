package com.companion.app.domain.model

/**
 * Memória emocional básica do Companion
 * Não invasiva, não julgadora, evolui naturalmente
 */
data class EmotionalMemory(
    val userName: String = "",
    val avatarName: String = "",
    val companionType: String = "", // FRIEND, CONFIDANT, NEUTRAL
    val recurringThemes: List<String> = emptyList(),
    val recentMood: String? = null,
    val conversationCount: Int = 0,
    val lastInteraction: Long = 0L
) {
    fun addTheme(theme: String) {
        // Implementação futura - por enquanto apenas estrutura
    }
    
    fun updateMood(mood: String) {
        // Implementação futura
    }
    
    fun incrementConversation() {
        // Implementação futura
    }
}

