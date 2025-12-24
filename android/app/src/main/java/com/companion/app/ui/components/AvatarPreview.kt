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
import androidx.compose.ui.unit.dp

/**
 * Preview do avatar com microanimações suaves
 */
@Composable
fun AvatarPreview(
    avatarName: String,
    style: String,
    bodyType: String,
    faceType: String,
    hair: String,
    skinTone: String,
    modifier: Modifier = Modifier
) {
    // Animações suaves
    val infiniteTransition = rememberInfiniteTransition(label = "avatar_animations")
    
    // Animação de piscar (ocasional)
    val blink by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(100, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "blink"
    )
    
    // Animação de respiração (contínua e suave)
    val breathe by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "breathe"
    )
    
    // Animação de inclinação leve da cabeça
    val headTilt by infiniteTransition.animateFloat(
        initialValue = -2f,
        targetValue = 2f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "head_tilt"
    )
    
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(280.dp)
            .padding(vertical = 32.dp),
        contentAlignment = Alignment.Center
    ) {
        // Fundo com gradiente suave
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.surface.copy(alpha = 0.3f),
                            MaterialTheme.colorScheme.surface.copy(alpha = 0.1f)
                        )
                    )
                )
        )
        
        // Avatar
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .scale(breathe)
                .rotate(headTilt)
        ) {
            // Cabeça e rosto
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(getSkinToneColor(skinTone)),
                contentAlignment = Alignment.Center
            ) {
                // Rosto baseado no tipo
                FaceShape(faceType = faceType)
                
                // Cabelo
                HairStyle(
                    hair = hair,
                    modifier = Modifier.fillMaxSize()
                )
                
                // Olhos (com animação de piscar)
                Row(
                    modifier = Modifier.offset(y = (-8).dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Olho esquerdo
                    Box(
                        modifier = Modifier
                            .size(12.dp, 8.dp)
                            .scale(scaleY = blink)
                            .clip(RoundedCornerShape(50))
                            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f))
                    )
                    // Olho direito
                    Box(
                        modifier = Modifier
                            .size(12.dp, 8.dp)
                            .scale(scaleY = blink)
                            .clip(RoundedCornerShape(50))
                            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f))
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Ombros/corpo (baseado no tipo de corpo)
            BodyShape(
                bodyType = bodyType,
                modifier = Modifier.size(width = 100.dp, height = 60.dp)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Nome do avatar
            if (avatarName.isNotBlank()) {
                androidx.compose.material3.Text(
                    text = avatarName,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
private fun FaceShape(faceType: String) {
    val shape = when (faceType) {
        "Redondo" -> CircleShape
        "Oval" -> RoundedCornerShape(50)
        "Quadrado" -> RoundedCornerShape(8.dp)
        "Alongado" -> RoundedCornerShape(50)
        else -> CircleShape
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize(0.85f)
            .clip(shape)
            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f))
    )
}

@Composable
private fun HairStyle(hair: String, modifier: Modifier = Modifier) {
    val hairColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
    
    when (hair) {
        "Curto" -> {
            Box(
                modifier = modifier
                    .offset(y = (-20).dp)
                    .size(80.dp, 30.dp)
                    .clip(RoundedCornerShape(bottomStart = 50, bottomEnd = 50))
                    .background(hairColor)
            )
        }
        "Médio" -> {
            Box(
                modifier = modifier
                    .offset(y = (-25).dp)
                    .size(90.dp, 40.dp)
                    .clip(RoundedCornerShape(bottomStart = 50, bottomEnd = 50))
                    .background(hairColor)
            )
        }
        "Longo" -> {
            Box(
                modifier = modifier
                    .offset(y = (-30).dp)
                    .size(100.dp, 50.dp)
                    .clip(RoundedCornerShape(bottomStart = 50, bottomEnd = 50))
                    .background(hairColor)
            )
        }
        "Sem cabelo" -> {
            // Sem cabelo - não renderiza nada
        }
    }
}

@Composable
private fun BodyShape(bodyType: String, modifier: Modifier = Modifier) {
    val bodyColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
    
    val widthMultiplier = when (bodyType) {
        "Esguio" -> 0.8f
        "Robusto" -> 1.2f
        else -> 1f // Médio
    }
    
    Box(
        modifier = modifier
            .fillMaxWidth(widthMultiplier)
            .clip(RoundedCornerShape(topStart = 50, topEnd = 50))
            .background(bodyColor)
    )
}

private fun getSkinToneColor(skinTone: String): Color {
    return when (skinTone) {
        "Claro" -> Color(0xFFFFDBB3)
        "Médio" -> Color(0xFFD4A574)
        "Escuro" -> Color(0xFF8B6F47)
        "Muito escuro" -> Color(0xFF5C4033)
        else -> Color(0xFFD4A574) // Default médio
    }
}

