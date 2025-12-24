package com.companion.app.data.local

import com.companion.app.domain.model.AvatarState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Repositório local para dados do Avatar
 * Por enquanto em memória, preparado para persistência futura
 */
class AvatarRepository {
    
    private val _avatarState = MutableStateFlow<AvatarState?>(null)
    val avatarState: StateFlow<AvatarState?> = _avatarState.asStateFlow()
    
    fun saveAvatar(avatar: AvatarState) {
        _avatarState.value = avatar
    }
    
    fun getAvatar(): AvatarState? = _avatarState.value
    
    fun hasAvatar(): Boolean = _avatarState.value != null
}

