package com.companion.app.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.companion.app.ui.components.ConversationMessage
import com.companion.app.ui.components.PresenceAvatar
import com.companion.app.ui.theme.CompanionTheme
import com.companion.app.ui.viewmodel.FirstConversationViewModel
import kotlinx.coroutines.delay

/**
 * Tela de primeira conversa
 * Foco em criar vínculo emocional e presença
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FirstConversationScreen(
    viewModel: FirstConversationViewModel,
    onConversationStarted: () -> Unit
) {
    val messages by viewModel.messages.collectAsState()
    val isResponding by viewModel.isResponding.collectAsState()
    val isListening by viewModel.isListening.collectAsState()
    
    var messageText by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    
    // Animações de entrada para textos de boas-vindas
    var showWelcome1 by remember { mutableStateOf(false) }
    var showWelcome2 by remember { mutableStateOf(false) }
    var showWelcome3 by remember { mutableStateOf(false) }
    var showConversation by remember { mutableStateOf(false) }
    
    // Sequência de animações de entrada
    LaunchedEffect(Unit) {
        delay(300)
        showWelcome1 = true
        delay(800)
        showWelcome2 = true
        delay(800)
        showWelcome3 = true
        delay(600)
        showConversation = true
    }
    
    // Scroll automático para última mensagem
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            delay(100)
            listState.animateScrollToItem(messages.size - 1)
        }
    }
    
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // TOPO — PRESENÇA VISUAL
        PresenceAvatar(
            modifier = Modifier.fillMaxWidth(),
            isListening = isListening,
            isResponding = isResponding
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // MENSAGEM DE BOAS-VINDAS
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Texto principal
            AnimatedWelcomeText(
                text = "Oi. Eu sou o Companion.",
                visible = showWelcome1,
                fontSize = 28.sp,
                fontWeight = FontWeight.Light
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Texto secundário
            AnimatedWelcomeText(
                text = "Estou aqui para te acompanhar ao longo do seu dia.",
                visible = showWelcome2,
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Texto complementar
            AnimatedWelcomeText(
                text = "Você pode conversar comigo sempre que quiser.",
                visible = showWelcome3,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // ÁREA DE CONVERSA
        if (showConversation) {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                itemsIndexed(messages) { index, message ->
                    ConversationMessage(
                        text = message.text,
                        isFromCompanion = message.isFromCompanion,
                        animateEntry = index == messages.size - 1
                    )
                }
                
                // Indicador de digitação (se estiver respondendo)
                if (isResponding) {
                    item {
                        TypingIndicator()
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // CAMPO DE TEXTO (INPUT)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = messageText,
                onValueChange = { messageText = it },
                modifier = Modifier.weight(1f),
                placeholder = {
                    Text(
                        text = "Escreva quando se sentir à vontade…",
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
                        fontSize = 15.sp
                    )
                },
                shape = RoundedCornerShape(24.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface
                ),
                singleLine = true,
                maxLines = 4
            )
            
            // Botão de enviar
            IconButton(
                onClick = {
                    if (messageText.isNotBlank()) {
                        viewModel.sendMessage(messageText)
                        messageText = ""
                    }
                },
                enabled = messageText.isNotBlank() && !isResponding,
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Enviar",
                    tint = if (messageText.isNotBlank() && !isResponding) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                    }
                )
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
private fun AnimatedWelcomeText(
    text: String,
    visible: Boolean,
    fontSize: androidx.compose.ui.unit.TextUnit,
    fontWeight: FontWeight
) {
    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(800, easing = FastOutSlowInEasing),
        label = "welcome_alpha"
    )
    
    val offsetY by animateDpAsState(
        targetValue = if (visible) 0.dp else 12.dp,
        animationSpec = tween(800, easing = FastOutSlowInEasing),
        label = "welcome_offset"
    )
    
    Text(
        text = text,
        fontSize = fontSize,
        fontWeight = fontWeight,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f),
        modifier = Modifier
            .alpha(alpha)
            .offset(y = offsetY),
        lineHeight = fontSize * 1.4f
    )
}

@Composable
private fun TypingIndicator() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f))
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            repeat(3) { index ->
                val delay = index * 200
                val infiniteTransition = rememberInfiniteTransition(label = "typing")
                val scale by infiniteTransition.animateFloat(
                    initialValue = 0.8f,
                    targetValue = 1f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(600, delayMillis = delay, easing = FastOutSlowInEasing),
                        repeatMode = RepeatMode.Reverse
                    ),
                    label = "typing_dot_$index"
                )
                
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .scale(scale)
                        .clip(RoundedCornerShape(50))
                        .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))
                )
            }
        }
    }
}

// ==================== PREVIEWS ====================

@Preview(
    name = "Primeira Conversa - Inicial",
    showBackground = true,
    widthDp = 360,
    heightDp = 800
)
@Composable
private fun FirstConversationScreenPreview() {
    CompanionTheme {
        FirstConversationScreen(
            viewModel = FirstConversationViewModel(),
            onConversationStarted = {}
        )
    }
}

@Preview(
    name = "Primeira Conversa - Com Mensagens Mockadas",
    showBackground = true,
    widthDp = 360,
    heightDp = 800
)
@Composable
private fun FirstConversationScreenWithMockMessagesPreview() {
    CompanionTheme {
        val mockViewModel = FirstConversationViewModel(mockMode = true)
        
        // Simular estado de conversa ativa
        var showWelcome1 by remember { mutableStateOf(true) }
        var showWelcome2 by remember { mutableStateOf(true) }
        var showWelcome3 by remember { mutableStateOf(true) }
        var showConversation by remember { mutableStateOf(true) }
        
        val messages by mockViewModel.messages.collectAsState()
        val isResponding by mockViewModel.isResponding.collectAsState()
        val isListening by mockViewModel.isListening.collectAsState()
        
        var messageText by remember { mutableStateOf("") }
        val listState = rememberLazyListState()
        
        // Scroll para última mensagem
        LaunchedEffect(messages.size) {
            if (messages.isNotEmpty()) {
                delay(100)
                listState.animateScrollToItem(messages.size - 1)
            }
        }
        
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Avatar
            PresenceAvatar(
                modifier = Modifier.fillMaxWidth(),
                isListening = isListening,
                isResponding = isResponding
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Mensagens de boas-vindas (já visíveis)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnimatedWelcomeText(
                    text = "Oi. Eu sou o Companion.",
                    visible = showWelcome1,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Light
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                AnimatedWelcomeText(
                    text = "Estou aqui para te acompanhar ao longo do seu dia.",
                    visible = showWelcome2,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                AnimatedWelcomeText(
                    text = "Você pode conversar comigo sempre que quiser.",
                    visible = showWelcome3,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                )
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Área de conversa
            if (showConversation) {
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    itemsIndexed(messages) { index, message ->
                        ConversationMessage(
                            text = message.text,
                            isFromCompanion = message.isFromCompanion,
                            animateEntry = false // Todas já visíveis no preview
                        )
                    }
                    
                    if (isResponding) {
                        item {
                            TypingIndicator()
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Campo de texto
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = messageText,
                    onValueChange = { messageText = it },
                    modifier = Modifier.weight(1f),
                    placeholder = {
                        Text(
                            text = "Escreva quando se sentir à vontade…",
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
                            fontSize = 15.sp
                        )
                    },
                    shape = RoundedCornerShape(24.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface
                    ),
                    singleLine = true,
                    maxLines = 4
                )
                
                IconButton(
                    onClick = { },
                    enabled = messageText.isNotBlank() && !isResponding,
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Enviar",
                        tint = if (messageText.isNotBlank() && !isResponding) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                        }
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

