package com.companion.app.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Seletor de tom de pele com círculos de cor
 */
@Composable
fun SkinToneSelector(
    selectedTone: String?,
    onToneSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val tones = listOf(
        "Claro" to Color(0xFFFFDBB3),
        "Médio" to Color(0xFFD4A574),
        "Escuro" to Color(0xFF8B6F47),
        "Muito escuro" to Color(0xFF5C4033)
    )
    
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        tones.forEach { (tone, color) ->
            val isSelected = selectedTone == tone
            val size by animateDpAsState(
                targetValue = if (isSelected) 56.dp else 48.dp,
                animationSpec = tween(200),
                label = "size"
            )
            
            val borderWidth by animateDpAsState(
                targetValue = if (isSelected) 3.dp else 0.dp,
                animationSpec = tween(200),
                label = "border"
            )
            
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable { onToneSelected(tone) }
            ) {
                Box(
                    modifier = Modifier
                        .size(size)
                        .clip(CircleShape)
                        .background(color)
                        .then(
                            if (isSelected) {
                                Modifier.border(
                                    width = borderWidth,
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = CircleShape
                                )
                            } else {
                                Modifier
                            }
                        )
                )
                Spacer(modifier = Modifier.height(4.dp))
                androidx.compose.material3.Text(
                    text = tone,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
        }
    }
}

