package com.companion.app.unity

import android.util.Log
import com.companion.app.domain.model.AvatarEmotionalState

/**
 * Handler para comunicação entre Android e Unity
 * Gerencia mensagens bidirecionais
 */
object UnityMessageHandler {
    
    private const val TAG = "UnityMessageHandler"
    private const val AVATAR_CONTROLLER_OBJECT = "AvatarController"
    
    /**
     * Envia estado emocional para Unity
     */
    fun sendEmotionalState(state: AvatarEmotionalState) {
        try {
            // TODO: Implementar quando Unity estiver integrado
            // UnityPlayer.UnitySendMessage(
            //     AVATAR_CONTROLLER_OBJECT,
            //     "SetEmotionalState",
            //     state.name
            // )
            
            Log.d(TAG, "Estado enviado para Unity: ${state.name}")
        } catch (e: Exception) {
            Log.e(TAG, "Erro ao enviar mensagem para Unity", e)
        }
    }
    
    /**
     * Envia dados do avatar para Unity
     * Usado quando avatar é criado/atualizado
     */
    fun sendAvatarData(avatarData: Map<String, String>) {
        try {
            // TODO: Converter para JSON e enviar
            // val json = Gson().toJson(avatarData)
            // UnityPlayer.UnitySendMessage(
            //     AVATAR_CONTROLLER_OBJECT,
            //     "UpdateAvatar",
            //     json
            // )
            
            Log.d(TAG, "Dados do avatar enviados para Unity")
        } catch (e: Exception) {
            Log.e(TAG, "Erro ao enviar dados do avatar", e)
        }
    }
    
    /**
     * Callback recebido do Unity (se necessário)
     * Unity pode chamar métodos Android via AndroidJavaClass
     */
    fun onUnityMessageReceived(message: String) {
        Log.d(TAG, "Mensagem recebida do Unity: $message")
        // Processar mensagem se necessário
    }
}

