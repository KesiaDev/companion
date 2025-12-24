package com.companion.app.ui.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.companion.app.ui.components.AvatarPreview
import com.companion.app.ui.components.CustomizationChip
import com.companion.app.ui.components.SkinToneSelector
import com.companion.app.ui.theme.CompanionTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AvatarCreationScreen(
    onAvatarCreated: () -> Unit
) {
    // Estados
    var avatarName by remember { mutableStateOf("") }
    var pronouns by remember { mutableStateOf("") }
    var selectedStyle by remember { mutableStateOf("") }
    var selectedBodyType by remember { mutableStateOf("") }
    var selectedFaceType by remember { mutableStateOf("") }
    var selectedHair by remember { mutableStateOf("") }
    var selectedSkinTone by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    
    // Opções
    val styles = listOf(
        "Casual" to Icons.Default.Person,
        "Formal" to Icons.Default.Work,
        "Relaxado" to Icons.Default.Home,
        "Moderno" to Icons.Default.Star
    )
    
    val bodyTypes = listOf("Médio", "Esguio", "Robusto")
    val faceTypes = listOf("Redondo", "Oval", "Quadrado", "Alongado")
    val hairOptions = listOf("Curto", "Médio", "Longo", "Sem cabelo")
    val pronounsOptions = listOf("Ela/Dela", "Ele/Dele", "Elu/Delu", "Personalizado")
    
    val isFormValid = avatarName.isNotBlank() &&
            selectedStyle.isNotBlank() &&
            selectedBodyType.isNotBlank() &&
            selectedFaceType.isNotBlank() &&
            selectedHair.isNotBlank() &&
            selectedSkinTone.isNotBlank()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        
        // Header
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Crie seu Companion",
                fontSize = 32.sp,
                fontWeight = FontWeight.Light,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Personalize o avatar do seu companheiro",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                style = MaterialTheme.typography.bodyMedium
            )
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Preview do Avatar (Elemento Principal)
        AvatarPreview(
            avatarName = avatarName,
            style = selectedStyle,
            bodyType = selectedBodyType,
            faceType = selectedFaceType,
            hair = selectedHair,
            skinTone = selectedSkinTone
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Formulário de Personalização
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // A. Nome do avatar
            OutlinedTextField(
                value = avatarName,
                onValueChange = { avatarName = it },
                label = { Text("Nome do avatar") },
                placeholder = { Text("Ex: Alex, Sam, Jordan") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                )
            )
            
            // B. Pronomes
            var pronounsExpanded by remember { mutableStateOf(false) }
            ExposedDropdownMenuBox(
                expanded = pronounsExpanded,
                onExpandedChange = { pronounsExpanded = !pronounsExpanded }
            ) {
                OutlinedTextField(
                    value = pronouns,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Pronomes") },
                    placeholder = { Text("Selecione os pronomes") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = pronounsExpanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                    )
                )
                ExposedDropdownMenu(
                    expanded = pronounsExpanded,
                    onDismissRequest = { pronounsExpanded = false }
                ) {
                    pronounsOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = {
                                pronouns = option
                                pronounsExpanded = false
                            }
                        )
                    }
                }
            }
            
            // C. Estilo (chips com ícones)
            SectionTitle("Estilo")
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                styles.forEach { (style, icon) ->
                    CustomizationChip(
                        label = style,
                        icon = icon,
                        isSelected = selectedStyle == style,
                        onClick = { selectedStyle = style },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            
            // D. Tipo de Corpo (silhuetas)
            SectionTitle("Tipo de corpo")
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                bodyTypes.forEach { type ->
                    CustomizationChip(
                        label = type,
                        isSelected = selectedBodyType == type,
                        onClick = { selectedBodyType = type },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            
            // E. Tipo de Rosto (geométrico)
            SectionTitle("Tipo de rosto")
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                faceTypes.forEach { type ->
                    val icon: androidx.compose.ui.graphics.vector.ImageVector? = when (type) {
                        "Redondo" -> Icons.Default.RadioButtonUnchecked
                        "Oval" -> Icons.Default.Lens
                        "Quadrado" -> Icons.Default.CropSquare
                        "Alongado" -> Icons.Default.CropFree
                        else -> Icons.Default.RadioButtonUnchecked
                    }
                    CustomizationChip(
                        label = type,
                        icon = icon,
                        isSelected = selectedFaceType == type,
                        onClick = { selectedFaceType = type },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            
            // F. Cabelo
            SectionTitle("Cabelo")
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                hairOptions.forEach { hair ->
                    CustomizationChip(
                        label = hair,
                        isSelected = selectedHair == hair,
                        onClick = { selectedHair = hair },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            
            // G. Tom de Pele
            SectionTitle("Tom de pele")
            SkinToneSelector(
                selectedTone = selectedSkinTone,
                onToneSelected = { selectedSkinTone = it },
                modifier = Modifier.fillMaxWidth()
            )
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Botão Final
        Button(
            onClick = {
                isLoading = true
                // TODO: Salvar via API
                onAvatarCreated()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            enabled = isFormValid && !isLoading,
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text(
                    text = "Criar Companion",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        fontSize = 14.sp,
        fontWeight = FontWeight.Medium,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

// ==================== PREVIEWS ====================

@Preview(
    name = "Tela Vazia",
    showBackground = true,
    widthDp = 360,
    heightDp = 800
)
@Composable
private fun AvatarCreationScreenEmptyPreview() {
    CompanionTheme {
        AvatarCreationScreen(
            onAvatarCreated = {}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(
    name = "Tela Preenchida (Mock)",
    showBackground = true,
    widthDp = 360,
    heightDp = 800
)
@Composable
private fun AvatarCreationScreenFilledPreview() {
    CompanionTheme {
        // Dados mockados para preview
        var avatarName by remember { mutableStateOf("Alex") }
        var pronouns by remember { mutableStateOf("Ele/Dele") }
        var selectedStyle by remember { mutableStateOf("Casual") }
        var selectedBodyType by remember { mutableStateOf("Médio") }
        var selectedFaceType by remember { mutableStateOf("Oval") }
        var selectedHair by remember { mutableStateOf("Médio") }
        var selectedSkinTone by remember { mutableStateOf("Médio") }
        
        val styles = listOf(
            "Casual" to Icons.Default.Person,
            "Formal" to Icons.Default.Work,
            "Relaxado" to Icons.Default.Home,
            "Moderno" to Icons.Default.Star
        )
        
        val bodyTypes = listOf("Médio", "Esguio", "Robusto")
        val faceTypes = listOf("Redondo", "Oval", "Quadrado", "Alongado")
        val hairOptions = listOf("Curto", "Médio", "Longo", "Sem cabelo")
        val pronounsOptions = listOf("Ela/Dela", "Ele/Dele", "Elu/Delu", "Personalizado")
        
        val isFormValid = avatarName.isNotBlank() &&
                selectedStyle.isNotBlank() &&
                selectedBodyType.isNotBlank() &&
                selectedFaceType.isNotBlank() &&
                selectedHair.isNotBlank() &&
                selectedSkinTone.isNotBlank()
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            
            // Header
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Crie seu Companion",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Light,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Personalize o avatar do seu companheiro",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Preview do Avatar
            AvatarPreview(
                avatarName = avatarName,
                style = selectedStyle,
                bodyType = selectedBodyType,
                faceType = selectedFaceType,
                hair = selectedHair,
                skinTone = selectedSkinTone
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Formulário
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize(),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // Nome
                OutlinedTextField(
                    value = avatarName,
                    onValueChange = { avatarName = it },
                    label = { Text("Nome do avatar") },
                    placeholder = { Text("Ex: Alex, Sam, Jordan") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                    )
                )
                
                // Pronomes
                var pronounsExpanded by remember { mutableStateOf(false) }
                ExposedDropdownMenuBox(
                    expanded = pronounsExpanded,
                    onExpandedChange = { pronounsExpanded = !pronounsExpanded }
                ) {
                    OutlinedTextField(
                        value = pronouns,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Pronomes") },
                        placeholder = { Text("Selecione os pronomes") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = pronounsExpanded) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                        )
                    )
                    ExposedDropdownMenu(
                        expanded = pronounsExpanded,
                        onDismissRequest = { pronounsExpanded = false }
                    ) {
                        pronounsOptions.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    pronouns = option
                                    pronounsExpanded = false
                                }
                            )
                        }
                    }
                }
                
                // Estilo
                SectionTitle("Estilo")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    styles.forEach { (style, icon) ->
                        CustomizationChip(
                            label = style,
                            icon = icon,
                            isSelected = selectedStyle == style,
                            onClick = { selectedStyle = style },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
                
                // Tipo de Corpo
                SectionTitle("Tipo de corpo")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    bodyTypes.forEach { type ->
                        CustomizationChip(
                            label = type,
                            isSelected = selectedBodyType == type,
                            onClick = { selectedBodyType = type },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
                
                // Tipo de Rosto
                SectionTitle("Tipo de rosto")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    faceTypes.forEach { type ->
                        val icon: androidx.compose.ui.graphics.vector.ImageVector? = when (type) {
                            "Redondo" -> Icons.Default.RadioButtonUnchecked
                            "Oval" -> Icons.Default.Lens
                            "Quadrado" -> Icons.Default.CropSquare
                            "Alongado" -> Icons.Default.CropFree
                            else -> Icons.Default.RadioButtonUnchecked
                        }
                        CustomizationChip(
                            label = type,
                            icon = icon,
                            isSelected = selectedFaceType == type,
                            onClick = { selectedFaceType = type },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
                
                // Cabelo
                SectionTitle("Cabelo")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    hairOptions.forEach { hair ->
                        CustomizationChip(
                            label = hair,
                            isSelected = selectedHair == hair,
                            onClick = { selectedHair = hair },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
                
                // Tom de Pele
                SectionTitle("Tom de pele")
                SkinToneSelector(
                    selectedTone = selectedSkinTone,
                    onToneSelected = { selectedSkinTone = it },
                    modifier = Modifier.fillMaxWidth()
                )
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Botão
            Button(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = isFormValid,
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Text(
                    text = "Criar Companion",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Preview(
    name = "Preview do Avatar - Completo",
    showBackground = true,
    widthDp = 360
)
@Composable
private fun AvatarPreviewCompletePreview() {
    CompanionTheme {
        AvatarPreview(
            avatarName = "Alex",
            style = "Casual",
            bodyType = "Médio",
            faceType = "Oval",
            hair = "Médio",
            skinTone = "Médio"
        )
    }
}

@Preview(
    name = "Preview do Avatar - Variações",
    showBackground = true,
    widthDp = 360
)
@Composable
private fun AvatarPreviewVariationsPreview() {
    CompanionTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            // Avatar 1: Claro, Curto, Redondo
            AvatarPreview(
                avatarName = "Sam",
                style = "Formal",
                bodyType = "Esguio",
                faceType = "Redondo",
                hair = "Curto",
                skinTone = "Claro"
            )
            
            // Avatar 2: Escuro, Longo, Quadrado
            AvatarPreview(
                avatarName = "Jordan",
                style = "Relaxado",
                bodyType = "Robusto",
                faceType = "Quadrado",
                hair = "Longo",
                skinTone = "Escuro"
            )
            
            // Avatar 3: Sem cabelo
            AvatarPreview(
                avatarName = "Taylor",
                style = "Moderno",
                bodyType = "Médio",
                faceType = "Oval",
                hair = "Sem cabelo",
                skinTone = "Médio"
            )
        }
    }
}
