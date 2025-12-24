package com.companion.app.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.companion.app.domain.model.AvatarEmotionalState
import com.companion.app.unity.AvatarStateManager

/**
 * Componente Compose que hospeda o avatar Unity
 * 
 * IMPORTANTE: Este componente NÃO desenha o avatar.
 * Apenas hospeda o container do Unity que renderiza o avatar 3D.
 */
@Composable
fun UnityAvatarHost(
    emotionalState: AvatarEmotionalState = AvatarEmotionalState.NEUTRAL,
    modifier: Modifier = Modifier,
    isListening: Boolean = false,
    isResponding: Boolean = false
) {
    val context = LocalContext.current
    val stateManager = remember { AvatarStateManager() }
    
    // Determinar estado emocional baseado em flags
    val currentState = when {
        isListening -> AvatarEmotionalState.ATTENTIVE
        isResponding -> AvatarEmotionalState.SMILING
        else -> emotionalState
    }
    
    // Atualizar estado quando mudar
    LaunchedEffect(currentState) {
        stateManager.setEmotionalState(currentState)
    }
    
    // Container que hospeda Unity
    Box(modifier = modifier) {
        AndroidView(
            factory = { ctx ->
                // TODO: Substituir por UnityPlayer quando Unity estiver integrado
                // Por enquanto, placeholder vazio
                android.view.View(ctx).apply {
                    // UnityPlayer será inicializado aqui
                    // val unityPlayer = UnityPlayer(ctx)
                    // stateManager.initialize(unityPlayer)
                    // return unityPlayer
                }
            },
            modifier = Modifier.fillMaxSize(),
            update = { view ->
                // Atualizar view se necessário
            }
        )
    }
    
    // Cleanup quando componente for removido
    DisposableEffect(Unit) {
        onDispose {
            stateManager.cleanup()
        }
    }
}

/**
 * Versão simplificada para previews
 * Mostra placeholder até Unity estar integrado
 */
@Composable
fun UnityAvatarHostPlaceholder(
    emotionalState: AvatarEmotionalState = AvatarEmotionalState.NEUTRAL,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        // Placeholder visual até Unity estar integrado
        // Será substituído por UnityPlayer
        androidx.compose.material3.Surface(
            modifier = Modifier.fillMaxSize(),
            color = androidx.compose.material3.MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                androidx.compose.material3.Text(
                    text = "Avatar Unity\n(Em integração)",
                    style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                    color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        }
    }
}

