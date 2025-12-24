package com.companion.app.domain.model

/**
 * Estado do avatar do Companion
 * Armazenado localmente e preparado para sincronização futura
 */
data class AvatarState(
    val name: String = "",
    val pronouns: String = "",
    val style: String = "",
    val bodyType: String = "",
    val faceType: String = "",
    val hair: String = "",
    val skinTone: String = ""
) {
    fun isValid(): Boolean {
        return name.isNotBlank() &&
                style.isNotBlank() &&
                bodyType.isNotBlank() &&
                faceType.isNotBlank() &&
                hair.isNotBlank() &&
                skinTone.isNotBlank()
    }
}

