package com.companion.app.domain.model

/**
 * Modelos para a camada de Comunidade
 * Preparação para implementação futura
 */

enum class CommunityTheme {
    LIGHT_CONVERSATION,  // Conversa leve
    WORK,                // Trabalho
    ANXIETY,             // Ansiedade
    JUST_TALK            // Só conversar
}

data class CommunityRoom(
    val id: String,
    val name: String,
    val theme: CommunityTheme,
    val description: String,
    val memberCount: Int = 0,
    val isActive: Boolean = true
)

data class CommunityMessage(
    val id: String,
    val roomId: String,
    val authorNickname: String, // Apelido anônimo
    val content: String,
    val timestamp: Long,
    val theme: CommunityTheme? = null
)

data class CommunityUser(
    val id: String,
    val nickname: String, // Apelido anônimo
    val avatarColor: String, // Cor do avatar para identificação visual
    val joinDate: Long
)

