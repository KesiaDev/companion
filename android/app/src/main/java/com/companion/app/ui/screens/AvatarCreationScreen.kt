package com.companion.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.companion.app.data.local.PreferencesManager
import com.companion.app.data.remote.RetrofitClient
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AvatarCreationScreen(
    onAvatarCreated: () -> Unit
) {
    val context = LocalContext.current
    val preferencesManager = PreferencesManager(context)
    
    var avatarName by remember { mutableStateOf("") }
    var selectedStyle by remember { mutableStateOf("") }
    var selectedBodyType by remember { mutableStateOf("") }
    var selectedFaceType by remember { mutableStateOf("") }
    var selectedHair by remember { mutableStateOf("") }
    var selectedSkinTone by remember { mutableStateOf("") }
    var pronouns by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    
    val styles = listOf("Casual", "Formal", "Relaxado", "Moderno")
    val bodyTypes = listOf("Médio", "Esguio", "Robusto")
    val faceTypes = listOf("Redondo", "Oval", "Quadrado", "Alongado")
    val hairOptions = listOf("Curto", "Médio", "Longo", "Sem cabelo")
    val skinTones = listOf("Claro", "Médio", "Escuro", "Muito escuro")
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Crie seu Companion",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "Personalize o avatar do seu companheiro",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Nome do avatar
        OutlinedTextField(
            value = avatarName,
            onValueChange = { avatarName = it },
            label = { Text("Nome do avatar") },
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Pronomes
        OutlinedTextField(
            value = pronouns,
            onValueChange = { pronouns = it },
            label = { Text("Pronomes (ex: ele/dele, ela/dela)") },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Opcional") }
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Estilo
        Text("Estilo", fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            styles.forEach { style ->
                FilterChip(
                    selected = selectedStyle == style,
                    onClick = { selectedStyle = style },
                    label = { Text(style) }
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Tipo de corpo
        Text("Tipo de corpo", fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            bodyTypes.forEach { type ->
                FilterChip(
                    selected = selectedBodyType == type,
                    onClick = { selectedBodyType = type },
                    label = { Text(type) }
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Tipo de rosto
        Text("Tipo de rosto", fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            faceTypes.forEach { type ->
                FilterChip(
                    selected = selectedFaceType == type,
                    onClick = { selectedFaceType = type },
                    label = { Text(type) }
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Cabelo
        Text("Cabelo", fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            hairOptions.forEach { hair ->
                FilterChip(
                    selected = selectedHair == hair,
                    onClick = { selectedHair = hair },
                    label = { Text(hair) }
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Tom de pele
        Text("Tom de pele", fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            skinTones.forEach { tone ->
                FilterChip(
                    selected = selectedSkinTone == tone,
                    onClick = { selectedSkinTone = tone },
                    label = { Text(tone) }
                )
            }
        }
        
        Spacer(modifier = Modifier.weight(1f))
        
        Button(
            onClick = {
                isLoading = true
                // TODO: Implementar salvamento via OnboardingViewModel
                // Por enquanto, apenas navega
                onAvatarCreated()
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading && avatarName.isNotBlank() && 
                     selectedStyle.isNotBlank() && selectedBodyType.isNotBlank() &&
                     selectedFaceType.isNotBlank() && selectedHair.isNotBlank() &&
                     selectedSkinTone.isNotBlank()
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(20.dp))
            } else {
                Text("Criar Companion")
            }
        }
    }
}

