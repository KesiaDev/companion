package com.companion.app.data.model

// Auth
data class RegisterRequest(
    val email: String,
    val password: String,
    val name: String? = null,
    val age: Int? = null
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class AuthResponse(
    val user: User,
    val token: String
)

data class User(
    val id: String,
    val email: String,
    val name: String?
)

// Onboarding
data class OnboardingRequest(
    val companionType: String, // FRIEND, CONFIDANT, NEUTRAL
    val conversationTone: String,
    val avatar: AvatarData
)

data class AvatarData(
    val avatarName: String,
    val avatarStyle: String,
    val avatarBodyType: String,
    val avatarFaceType: String,
    val avatarHair: String,
    val avatarSkinTone: String,
    val pronouns: String? = null
)

data class OnboardingResponse(
    val success: Boolean
)

// Chat
data class ChatRequest(
    val message: String
)

data class ChatResponse(
    val response: String,
    val emotion: String?,
    val requiresSupport: Boolean?,
    val conversationId: String
)

// Memory
data class MemoryResponse(
    val userName: String?,
    val preferences: Map<String, Any>?,
    val recurringThemes: Map<String, Any>?,
    val frequentEmotions: Map<String, Any>?,
    val routine: Map<String, Any>?,
    val lastInteraction: String?,
    val interactionCount: Int
)

// Avatar
data class AvatarUpdateRequest(
    val avatarName: String? = null,
    val avatarStyle: String? = null,
    val avatarBodyType: String? = null,
    val avatarFaceType: String? = null,
    val avatarHair: String? = null,
    val avatarSkinTone: String? = null,
    val pronouns: String? = null
)

data class AvatarResponse(
    val id: String,
    val userId: String,
    val avatarName: String,
    val avatarStyle: String,
    val avatarBodyType: String,
    val avatarFaceType: String,
    val avatarHair: String,
    val avatarSkinTone: String,
    val pronouns: String?
)

// Report
data class ReportRequest(
    val type: String, // inappropriate_content, emotional_distress, other
    val description: String? = null,
    val context: Map<String, Any>? = null
)

data class ReportResponse(
    val reportId: String,
    val success: Boolean
)


