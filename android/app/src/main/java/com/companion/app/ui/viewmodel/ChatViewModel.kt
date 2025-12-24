package com.companion.app.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.companion.app.data.remote.ApiService
import com.companion.app.ui.screens.ChatMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChatViewModel(
    private val apiService: ApiService,
    private val token: String
) : ViewModel() {
    
    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _requiresSupport = MutableStateFlow(false)
    val requiresSupport: StateFlow<Boolean> = _requiresSupport.asStateFlow()
    
    fun addInitialMessage() {
        val initialMessage = ChatMessage(
            text = "Oi. Eu sou o Companion. Estou aqui para te acompanhar ao longo do seu dia. Você pode conversar comigo sempre que quiser.",
            isUser = false
        )
        _messages.value = listOf(initialMessage)
    }
    
    fun sendMessage(message: String) {
        // Adicionar mensagem do usuário
        val userMessage = ChatMessage(text = message, isUser = true)
        _messages.value = _messages.value + userMessage
        
        _isLoading.value = true
        
        viewModelScope.launch {
            try {
                // Verificar se há token
                if (token.isBlank()) {
                    Log.e("ChatViewModel", "Token vazio ou ausente")
                    val errorMessage = ChatMessage(
                        text = "Você precisa fazer login novamente. Por favor, saia e entre no app.",
                        isUser = false
                    )
                    _messages.value = _messages.value + errorMessage
                    _isLoading.value = false
                    return@launch
                }
                
                Log.d("ChatViewModel", "Enviando mensagem: $message")
                Log.d("ChatViewModel", "Token presente: ${token.take(10)}...")
                
                val response = apiService.sendMessage(
                    "Bearer $token",
                    com.companion.app.data.model.ChatRequest(message)
                )
                
                Log.d("ChatViewModel", "Resposta recebida: ${response.code()}")
                
                if (response.isSuccessful && response.body() != null) {
                    val body = response.body()!!
                    Log.d("ChatViewModel", "Resposta do Companion: ${body.response}")
                    val companionMessage = ChatMessage(
                        text = body.response,
                        isUser = false,
                        emotion = body.emotion
                    )
                    _messages.value = _messages.value + companionMessage
                    _requiresSupport.value = body.requiresSupport == true
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("ChatViewModel", "Erro na resposta: ${response.code()} - $errorBody")
                    val errorMessage = ChatMessage(
                        text = "Desculpe, tive um problema (${response.code()}). Pode tentar novamente?",
                        isUser = false
                    )
                    _messages.value = _messages.value + errorMessage
                }
            } catch (e: java.net.UnknownHostException) {
                Log.e("ChatViewModel", "Erro de conexão: Backend não encontrado", e)
                val errorMessage = ChatMessage(
                    text = "Não consegui conectar ao servidor. Verifique se o backend está rodando.",
                    isUser = false
                )
                _messages.value = _messages.value + errorMessage
            } catch (e: java.net.ConnectException) {
                Log.e("ChatViewModel", "Erro de conexão: Não foi possível conectar", e)
                val errorMessage = ChatMessage(
                    text = "Não consegui conectar ao servidor. Verifique se o backend está rodando na porta 3001.",
                    isUser = false
                )
                _messages.value = _messages.value + errorMessage
            } catch (e: Exception) {
                Log.e("ChatViewModel", "Erro ao enviar mensagem", e)
                val errorMessage = ChatMessage(
                    text = "Desculpe, estou tendo dificuldades técnicas. Como você está se sentindo hoje?",
                    isUser = false
                )
                _messages.value = _messages.value + errorMessage
            } finally {
                _isLoading.value = false
            }
        }
    }
}


