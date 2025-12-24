package com.companion.app.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.companion.app.ui.theme.CompanionTheme

/**
 * Avatar com presença - versão para primeira conversa
 * Com microanimações sutis que transmitem presença e calma
 */
@Composable
fun PresenceAvatar(
    modifier: Modifier = Modifier,
    isListening: Boolean = false,
    isResponding: Boolean = false
) {
    // Animações suaves e contínuas
    val infiniteTransition = rememberInfiniteTransition(label = "presence_animations")
    
    // Respiração suave (contínua)
    val breathe by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.03f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "breathe"
    )
    
    // Piscar ocasional (mais lento e sutil)
    val blink by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(150, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "blink"
    )
    
    // Inclinação leve da cabeça (muito sutil)
    val headTilt by infiniteTransition.animateFloat(
        initialValue = -1.5f,
        targetValue = 1.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "head_tilt"
    )
    
    // Animação de "ouvir" (quando isListening = true)
    val listeningScale by animateFloatAsState(
        targetValue = if (isListening) 1.05f else 1f,
        animationSpec = tween(800, easing = FastOutSlowInEasing),
        label = "listening"
    )
    
    // Animação de "responder" (quando isResponding = true)
    val respondingPulse by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = if (isResponding) 1.02f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "responding"
    )
    
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(vertical = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        // Fundo com gradiente muito sutil
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .clip(RoundedCornerShape(32.dp))
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.surface.copy(alpha = 0.2f),
                            MaterialTheme.colorScheme.surface.copy(alpha = 0.05f)
                        )
                    )
                )
        )
        
        // Avatar com todas as animações
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .scale(breathe * listeningScale * respondingPulse)
                .rotate(headTilt)
        ) {
            // Cabeça e rosto
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFD4A574)), // Tom de pele médio
                contentAlignment = Alignment.Center
            ) {
                // Rosto oval sutil
                Box(
                    modifier = Modifier
                        .fillMaxSize(0.85f)
                        .clip(RoundedCornerShape(50))
                        .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.03f))
                )
                
                // Cabelo médio
                Box(
                    modifier = Modifier
                        .offset(y = (-22).dp)
                        .size(85.dp, 35.dp)
                        .clip(RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp, bottomEnd = 50.dp, bottomStart = 50.dp))
                        .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                )
                
                // Olhos (com animação de piscar)
                Row(
                    modifier = Modifier.offset(y = (-6).dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Olho esquerdo
                    Box(
                        modifier = Modifier
                            .size(10.dp, 7.dp)
                            .scale(scaleX = 1f, scaleY = blink)
                            .clip(RoundedCornerShape(50))
                            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f))
                    )
                    // Olho direito
                    Box(
                        modifier = Modifier
                            .size(10.dp, 7.dp)
                            .scale(scaleX = 1f, scaleY = blink)
                            .clip(RoundedCornerShape(50))
                            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f))
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(6.dp))
            
            // Ombros/corpo
            Box(
                modifier = Modifier
                    .size(width = 90.dp, height = 50.dp)
                    .clip(RoundedCornerShape(topStart = 50.dp, topEnd = 50.dp, bottomEnd = 0.dp, bottomStart = 0.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.25f))
            )
        }
    }
}

// ==================== PREVIEWS ====================

@Preview(
    name = "Presence Avatar - Normal",
    showBackground = true,
    widthDp = 360
)
@Composable
private fun PresenceAvatarNormalPreview() {
    CompanionTheme {
        PresenceAvatar()
    }
}

@Preview(
    name = "Presence Avatar - Ouvindo",
    showBackground = true,
    widthDp = 360
)
@Composable
private fun PresenceAvatarListeningPreview() {
    CompanionTheme {
        PresenceAvatar(isListening = true)
    }
}

@Preview(
    name = "Presence Avatar - Respondendo",
    showBackground = true,
    widthDp = 360
)
@Composable
private fun PresenceAvatarRespondingPreview() {
    CompanionTheme {
        PresenceAvatar(isResponding = true)
    }
}

