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
fun CompanionTypeScreen(
    onTypeSelected: (String) -> Unit
) {
    var selectedType by remember { mutableStateOf<String?>(null) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Escolha o tipo de companhia",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "Como você gostaria que eu te acompanhasse?",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        CompanionTypeOption(
            title = "Amigo(a)",
            description = "Uma presença amigável e acolhedora",
            isSelected = selectedType == "FRIEND",
            onClick = { selectedType = "FRIEND" }
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        CompanionTypeOption(
            title = "Confidente",
            description = "Alguém para compartilhar pensamentos e sentimentos",
            isSelected = selectedType == "CONFIDANT",
            onClick = { selectedType = "CONFIDANT" }
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        CompanionTypeOption(
            title = "Companheiro emocional neutro",
            description = "Presença calma e neutra para o seu dia",
            isSelected = selectedType == "NEUTRAL",
            onClick = { selectedType = "NEUTRAL" }
        )
        
        Spacer(modifier = Modifier.weight(1f))
        
        Button(
            onClick = { selectedType?.let { onTypeSelected(it) } },
            modifier = Modifier.fillMaxWidth(),
            enabled = selectedType != null
        ) {
            Text("Continuar")
        }
    }
}

@Composable
fun CompanionTypeOption(
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

