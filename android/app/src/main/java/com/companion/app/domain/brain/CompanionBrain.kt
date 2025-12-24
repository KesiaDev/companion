package com.companion.app.domain.brain

import com.companion.app.domain.model.EmotionalMemory
import kotlinx.coroutines.delay
import kotlin.random.Random

/**
 * Cérebro do Companion - Camada de IA Mock
 * 
 * Responsabilidades:
 * - Processar mensagens do usuário
 * - Gerar respostas empáticas
 * - Simular delay humano
 * - Preparado para substituição por IA real
 */
class CompanionBrain(
    private var memory: EmotionalMemory
) {
    
    /**
     * Processa uma mensagem do usuário e retorna resposta empática
     * Simula delay humano de 1-2 segundos
     */
    suspend fun processMessage(
        userMessage: String,
        updateMemory: (EmotionalMemory) -> Unit
    ): String {
        // Simular delay humano
        delay((1000..2000).random().toLong())
        
        // Analisar sentimento da mensagem
        val sentiment = analyzeSentiment(userMessage)
        
        // Atualizar memória (básico)
        memory = memory.copy(
            conversationCount = memory.conversationCount + 1,
            lastInteraction = System.currentTimeMillis()
        )
        
        // Extrair tema se relevante
        val theme = extractTheme(userMessage)
        if (theme != null && !memory.recurringThemes.contains(theme)) {
            memory = memory.copy(
                recurringThemes = memory.recurringThemes + theme
            )
        }
        
        // Atualizar humor recente
        if (sentiment.mood != null) {
            memory = memory.copy(recentMood = sentiment.mood)
        }
        
        // Atualizar memória
        updateMemory(memory)
        
        // Gerar resposta empática
        return generateEmpatheticResponse(userMessage, sentiment)
    }
    
    /**
     * Analisa o sentimento da mensagem
     */
    private fun analyzeSentiment(message: String): Sentiment {
        val lowerMessage = message.lowercase()
        
        return when {
            // Sentimentos negativos
            lowerMessage.contains("mal") || lowerMessage.contains("ruim") ||
            lowerMessage.contains("triste") || lowerMessage.contains("ansioso") ||
            lowerMessage.contains("preocupado") || lowerMessage.contains("estressado") -> {
                Sentiment(
                    mood = "ansioso",
                    intensity = 0.7f,
                    needsSupport = false
                )
            }
            
            // Sentimentos positivos
            lowerMessage.contains("bem") || lowerMessage.contains("bom") ||
            lowerMessage.contains("feliz") || lowerMessage.contains("alegre") ||
            lowerMessage.contains("ótimo") -> {
                Sentiment(
                    mood = "feliz",
                    intensity = 0.6f,
                    needsSupport = false
                )
            }
            
            // Sentimentos neutros
            lowerMessage.contains("ok") || lowerMessage.contains("normal") ||
            lowerMessage.contains("tudo bem") || lowerMessage.contains("tranquilo") -> {
                Sentiment(
                    mood = "neutro",
                    intensity = 0.5f,
                    needsSupport = false
                )
            }
            
            // Padrão
            else -> {
                Sentiment(
                    mood = null,
                    intensity = 0.5f,
                    needsSupport = false
                )
            }
        }
    }
    
    /**
     * Extrai tema recorrente da mensagem
     */
    private fun extractTheme(message: String): String? {
        val lowerMessage = message.lowercase()
        
        return when {
            lowerMessage.contains("trabalho") || lowerMessage.contains("trabalhar") ||
            lowerMessage.contains("escritório") || lowerMessage.contains("chefe") -> "trabalho"
            
            lowerMessage.contains("família") || lowerMessage.contains("pais") ||
            lowerMessage.contains("irmão") || lowerMessage.contains("filho") -> "família"
            
            lowerMessage.contains("amigo") || lowerMessage.contains("amiga") ||
            lowerMessage.contains("relacionamento") -> "relacionamentos"
            
            lowerMessage.contains("estudo") || lowerMessage.contains("escola") ||
            lowerMessage.contains("faculdade") || lowerMessage.contains("prova") -> "estudos"
            
            lowerMessage.contains("saúde") || lowerMessage.contains("médico") ||
            lowerMessage.contains("dormir") || lowerMessage.contains("cansado") -> "saúde"
            
            else -> null
        }
    }
    
    /**
     * Gera resposta empática baseada na mensagem e sentimento
     */
    private fun generateEmpatheticResponse(
        userMessage: String,
        sentiment: Sentiment
    ): String {
        val lowerMessage = userMessage.lowercase()
        
        return when {
            // Sentimentos negativos - respostas acolhedoras
            sentiment.mood == "ansioso" || sentiment.mood == "triste" -> {
                listOf(
                    "Entendo. Obrigado por me contar.",
                    "Fico aqui com você.",
                    "Quer me falar um pouco mais, ou prefere só conversar?",
                    "Obrigado por compartilhar isso comigo.",
                    "Estou aqui se precisar conversar."
                ).random()
            }
            
            // Sentimentos positivos - validação
            sentiment.mood == "feliz" -> {
                listOf(
                    "Que bom saber disso.",
                    "Fico feliz em ouvir isso.",
                    "É bom saber que você está bem.",
                    "Que legal! Quer me contar mais?"
                ).random()
            }
            
            // Perguntas do usuário
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
            
            // Respostas sobre temas específicos
            lowerMessage.contains("trabalho") -> {
                listOf(
                    "Trabalho pode ser desafiador. Como você se sente sobre isso?",
                    "Entendo. Trabalho é uma parte importante do dia. Quer conversar mais sobre isso?",
                    "Obrigado por compartilhar. Como está sendo sua semana?"
                ).random()
            }
            
            // Default - resposta acolhedora genérica
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
    
    /**
     * Atualiza a memória do Companion
     */
    fun updateMemory(newMemory: EmotionalMemory) {
        memory = newMemory
    }
    
    /**
     * Obtém a memória atual
     */
    fun getMemory(): EmotionalMemory = memory
}

/**
 * Estrutura de sentimento analisado
 */
private data class Sentiment(
    val mood: String?,
    val intensity: Float,
    val needsSupport: Boolean
)

