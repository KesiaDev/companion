package com.companion.app.unity

import android.util.Log
import com.companion.app.domain.model.AvatarEmotionalState

/**
 * Gerenciador de estados do avatar Unity
 * Responsável por comunicar estados emocionais com Unity
 */
class AvatarStateManager {
    
    private var currentState: AvatarEmotionalState = AvatarEmotionalState.NEUTRAL
    private var unityPlayer: Any? = null // UnityPlayer será tipado quando integrado
    
    /**
     * Define o estado emocional do avatar
     * Envia mensagem para Unity via UnityMessage
     */
    fun setEmotionalState(state: AvatarEmotionalState) {
        if (currentState == state) {
            return // Evitar chamadas desnecessárias
        }
        
        currentState = state
        
        try {
            // TODO: Implementar quando Unity estiver integrado
            // UnityPlayer.UnitySendMessage(
            //     "AvatarController",      // GameObject name
            //     "SetEmotionalState",     // Method name
            //     state.name               // Parameter
            // )
            
            Log.d("AvatarStateManager", "Estado emocional alterado: ${state.name}")
        } catch (e: Exception) {
            Log.e("AvatarStateManager", "Erro ao comunicar com Unity", e)
        }
    }
    
    /**
     * Obtém o estado atual
     */
    fun getCurrentState(): AvatarEmotionalState = currentState
    
    /**
     * Inicializa o gerenciador
     * Deve ser chamado quando Unity estiver pronto
     */
    fun initialize(unityPlayerInstance: Any) {
        unityPlayer = unityPlayerInstance
        setEmotionalState(AvatarEmotionalState.NEUTRAL)
    }
    
    /**
     * Limpa recursos
     */
    fun cleanup() {
        unityPlayer = null
    }
}

