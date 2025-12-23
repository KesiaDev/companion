package com.companion.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ConversationToneScreen(
    onToneSelected: (String) -> Unit
) {
    var selectedTone by remember { mutableStateOf<String?>(null) }
    
    val tones = listOf(
        "Calmo" to "Conversas serenas e tranquilas",
        "Animado" to "Presença mais energética e positiva",
        "Neutro" to "Tom equilibrado e natural",
        "Empático" to "Foco em compreensão e apoio emocional"
    )
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Escolha o tom de conversa",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "Como você prefere que eu me comunique?",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        tones.forEach { (tone, description) ->
            ConversationToneOption(
                title = tone,
                description = description,
                isSelected = selectedTone == tone,
                onClick = { selectedTone = tone }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        
        Spacer(modifier = Modifier.weight(1f))
        
        Button(
            onClick = { selectedTone?.let { onToneSelected(it) } },
            modifier = Modifier.fillMaxWidth(),
            enabled = selectedTone != null
        ) {
            Text("Continuar")
        }
    }
}

@Composable
fun ConversationToneOption(
    title: String,
    description: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surface
            }
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = description,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}

