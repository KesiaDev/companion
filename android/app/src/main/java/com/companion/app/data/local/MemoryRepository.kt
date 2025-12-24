package com.companion.app.data.local

import com.companion.app.domain.model.EmotionalMemory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Repositório local para memória emocional
 * Por enquanto em memória, preparado para persistência futura
 */
class MemoryRepository {
    
    private val _memory = MutableStateFlow<EmotionalMemory>(
        EmotionalMemory()
    )
    val memory: StateFlow<EmotionalMemory> = _memory.asStateFlow()
    
    fun updateMemory(newMemory: EmotionalMemory) {
        _memory.value = newMemory
    }
    
    fun getMemory(): EmotionalMemory = _memory.value
    
    fun initializeMemory(
        userName: String,
        avatarName: String,
        companionType: String
    ) {
        _memory.value = EmotionalMemory(
            userName = userName,
            avatarName = avatarName,
            companionType = companionType,
            conversationCount = 0,
            lastInteraction = System.currentTimeMillis()
        )
    }
}

