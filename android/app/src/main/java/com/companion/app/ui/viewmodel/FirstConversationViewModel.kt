package com.companion.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ConversationMessage(
    val text: String,
    val isFromCompanion: Boolean
)

class FirstConversationViewModel : ViewModel() {
    
    private val _messages = MutableStateFlow<List<ConversationMessage>>(emptyList())
    val messages: StateFlow<List<ConversationMessage>> = _messages.asStateFlow()
    
    private val _isResponding = MutableStateFlow(false)
    val isResponding: StateFlow<Boolean> = _isResponding.asStateFlow()
    
    private val _isListening = MutableStateFlow(false)
    val isListening: StateFlow<Boolean> = _isListening.asStateFlow()
    
    // Modo mockado para previews
    var mockMode: Boolean = false
    
    init {
        // Adicionar mensagem inicial após um delay
        viewModelScope.launch {
            delay(2000) // Aguardar animações de boas-vindas
            addInitialMessage()
        }
    }
    
    // Construtor para modo mockado
    constructor(mockMode: Boolean) : this() {
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
            // Delay humano (1-2 segundos)
            delay((1000..2000).random().toLong())
            
            _isListening.value = false
            _isResponding.value = true
            
            // Gerar resposta calma e acolhedora
            val response = generateCalmResponse(message)
            
            delay(800) // Pequeno delay antes de mostrar resposta
            
            val companionMessage = ConversationMessage(
                text = response,
                isFromCompanion = true
            )
            _messages.value = _messages.value + companionMessage
            
            _isResponding.value = false
        }
    }
    
    /**
     * Gera respostas calmas e acolhedoras
     * Não terapêutico, não invasivo, apenas presente
     */
    private fun generateCalmResponse(userMessage: String): String {
        val lowerMessage = userMessage.lowercase()
        
        return when {
            // Sentimentos negativos
            lowerMessage.contains("mal") || lowerMessage.contains("ruim") || 
            lowerMessage.contains("triste") || lowerMessage.contains("ansioso") -> {
                listOf(
                    "Entendo. Obrigado por me contar.",
                    "Fico aqui com você.",
                    "Quer me falar um pouco mais, ou prefere só conversar?"
                ).random()
            }
            
            // Sentimentos neutros
            lowerMessage.contains("ok") || lowerMessage.contains("normal") ||
            lowerMessage.contains("tudo bem") -> {
                listOf(
                    "Entendo. Como foi seu dia até agora?",
                    "Obrigado por compartilhar.",
                    "Estou aqui se quiser conversar sobre qualquer coisa."
                ).random()
            }
            
            // Sentimentos positivos
            lowerMessage.contains("bem") || lowerMessage.contains("bom") ||
            lowerMessage.contains("feliz") -> {
                listOf(
                    "Que bom saber disso.",
                    "Fico feliz em ouvir isso.",
                    "É bom saber que você está bem."
                ).random()
            }
            
            // Perguntas
            lowerMessage.contains("?") -> {
                listOf(
                    "Boa pergunta. O que você acha?",
                    "Não tenho certeza. Mas quero entender melhor o que você sente.",
                    "Não sei ao certo. Mas estou aqui para conversar."
                ).random()
            }
            
            // Respostas curtas
            userMessage.length < 10 -> {
                listOf(
                    "Entendo.",
                    "Obrigado por me contar.",
                    "Fico aqui com você."
                ).random()
            }
            
            // Default - resposta acolhedora
            else -> {
                listOf(
                    "Entendo. Obrigado por compartilhar isso comigo.",
                    "Fico aqui com você.",
                    "Quer me falar mais sobre isso, ou prefere só conversar sobre outra coisa?",
                    "Obrigado por me contar. Como você se sente sobre isso?"
                ).random()
            }
        }
    }
}

