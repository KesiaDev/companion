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
fun AgeVerificationScreen(
    onVerified: () -> Unit
) {
    var ageConfirmed by remember { mutableStateOf(false) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Confirmação de Idade",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Text(
            text = "O COMPANION é um aplicativo para pessoas de 15 anos ou mais.",
            fontSize = 16.sp
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "Você confirma que tem 15 anos ou mais?",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedButton(
                onClick = { /* Fechar app ou mostrar mensagem */ },
                modifier = Modifier.weight(1f)
            ) {
                Text("Não")
            }
            
            Button(
                onClick = { ageConfirmed = true },
                modifier = Modifier.weight(1f)
            ) {
                Text("Sim")
            }
        }
        
        if (ageConfirmed) {
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onVerified,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Continuar")
            }
        }
    }
}


