package com.companion.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.companion.app.data.local.MemoryRepository
import com.companion.app.domain.brain.CompanionBrain
import com.companion.app.domain.model.EmotionalMemory
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ConversationMessage(
    val text: String,
    val isFromCompanion: Boolean
)

class FirstConversationViewModel(
    private val memoryRepository: MemoryRepository? = null
) : ViewModel() {
    
    private val _messages = MutableStateFlow<List<ConversationMessage>>(emptyList())
    val messages: StateFlow<List<ConversationMessage>> = _messages.asStateFlow()
    
    private val _isResponding = MutableStateFlow(false)
    val isResponding: StateFlow<Boolean> = _isResponding.asStateFlow()
    
    private val _isListening = MutableStateFlow(false)
    val isListening: StateFlow<Boolean> = _isListening.asStateFlow()
    
    // CompanionBrain - camada de IA mock
    private val companionBrain: CompanionBrain = CompanionBrain(
        memory = memoryRepository?.getMemory() ?: EmotionalMemory()
    )
    
    // Modo mockado para previews
    var mockMode: Boolean = false
    
    init {
        // Adicionar mensagem inicial após um delay
        viewModelScope.launch {
            delay(2000) // Aguardar animações de boas-vindas
            addInitialMessage()
        }
    }
    
    // Construtor secundário para modo mockado (previews)
    constructor(mockMode: Boolean) : this(null) {
        this.mockMode = mockMode
        if (mockMode) {
            loadMockConversation()
        }
    }
    
    private fun loadMockConversation() {
        _messages.value = listOf(
            ConversationMessage(
                text = "Como você está se sentindo agora?",
                isFromCompanion = true
            ),
            ConversationMessage(
                text = "Estou me sentindo um pouco ansioso hoje.",
                isFromCompanion = false
            ),
            ConversationMessage(
                text = "Entendo. Obrigado por me contar.",
                isFromCompanion = true
            ),
            ConversationMessage(
                text = "Fico aqui com você.",
                isFromCompanion = true
            ),
            ConversationMessage(
                text = "Obrigado. Isso ajuda.",
                isFromCompanion = false
            ),
            ConversationMessage(
                text = "Quer me falar um pouco mais, ou prefere só conversar?",
                isFromCompanion = true
            )
        )
    }
    
    private fun addInitialMessage() {
        val initialMessage = ConversationMessage(
            text = "Como você está se sentindo agora?",
            isFromCompanion = true
        )
        _messages.value = listOf(initialMessage)
    }
    
    fun sendMessage(message: String) {
        // Adicionar mensagem do usuário
        val userMessage = ConversationMessage(
            text = message,
            isFromCompanion = false
        )
        _messages.value = _messages.value + userMessage
        
        // Simular "ouvindo"
        _isListening.value = true
        
        viewModelScope.launch {
            // Usar CompanionBrain para processar mensagem
            val response = companionBrain.processMessage(message) { updatedMemory ->
                // Atualizar memória no repositório
                memoryRepository?.updateMemory(updatedMemory)
                // Atualizar memória no brain
                companionBrain.updateMemory(updatedMemory)
            }
            
            _isListening.value = false
            _isResponding.value = true
            
            delay(800) // Pequeno delay antes de mostrar resposta
            
            val companionMessage = ConversationMessage(
                text = response,
                isFromCompanion = true
            )
            _messages.value = _messages.value + companionMessage
            
            _isResponding.value = false
        }
    }
}

