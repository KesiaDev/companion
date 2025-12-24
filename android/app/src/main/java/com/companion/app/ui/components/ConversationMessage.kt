package com.companion.app.ui.components

import androidx.compose.animation.core.*
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.companion.app.ui.theme.CompanionTheme

/**
 * Mensagem de conversa minimalista
 * Estilo calmo e acolhedor
 */
@Composable
fun ConversationMessage(
    text: String,
    isFromCompanion: Boolean,
    modifier: Modifier = Modifier,
    animateEntry: Boolean = true
) {
    // Animação de entrada suave
    val alpha by animateFloatAsState(
        targetValue = if (animateEntry) 1f else 0f,
        animationSpec = tween(600, easing = FastOutSlowInEasing),
        label = "alpha"
    )
    
    val offsetY by animateDpAsState(
        targetValue = if (animateEntry) 0.dp else 8.dp,
        animationSpec = tween(600, easing = FastOutSlowInEasing),
        label = "offset"
    )
    
    Row(
        modifier = modifier
            .fillMaxWidth()
            .alpha(alpha)
            .then(Modifier.offset(y = offsetY)),
        horizontalArrangement = if (isFromCompanion) Arrangement.Start else Arrangement.End
    ) {
        if (isFromCompanion) {
            // Mensagem do Companion
            Box(
                modifier = Modifier
                    .widthIn(max = 280.dp)
                    .clip(RoundedCornerShape(20.dp, 20.dp, 20.dp, 4.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f))
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 16.sp,
                    lineHeight = 22.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f),
                    fontWeight = FontWeight.Normal
                )
            }
        } else {
            // Mensagem do usuário
            Box(
                modifier = Modifier
                    .widthIn(max = 280.dp)
                    .clip(RoundedCornerShape(20.dp, 20.dp, 4.dp, 20.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f))
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 16.sp,
                    lineHeight = 22.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.9f),
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}

// ==================== PREVIEWS ====================

@Preview(
    name = "Mensagem - Companion",
    showBackground = true,
    widthDp = 360
)
@Composable
private fun ConversationMessageCompanionPreview() {
    CompanionTheme {
        ConversationMessage(
            text = "Como você está se sentindo agora?",
            isFromCompanion = true
        )
    }
}

@Preview(
    name = "Mensagem - Usuário",
    showBackground = true,
    widthDp = 360
)
@Composable
private fun ConversationMessageUserPreview() {
    CompanionTheme {
        ConversationMessage(
            text = "Estou me sentindo um pouco ansioso hoje.",
            isFromCompanion = false
        )
    }
}
