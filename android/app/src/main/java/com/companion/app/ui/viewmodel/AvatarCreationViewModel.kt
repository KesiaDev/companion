package com.companion.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.companion.app.data.local.AvatarRepository
import com.companion.app.data.local.MemoryRepository
import com.companion.app.domain.model.AvatarState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AvatarCreationViewModel(
    private val avatarRepository: AvatarRepository,
    private val memoryRepository: MemoryRepository
) : ViewModel() {
    
    private val _avatarState = MutableStateFlow(AvatarState())
    val avatarState: StateFlow<AvatarState> = _avatarState.asStateFlow()
    
    val isFormValid: StateFlow<Boolean> = _avatarState.asStateFlow()
        .let { flow ->
            MutableStateFlow(false).apply {
                viewModelScope.launch {
                    flow.collect { avatar ->
                        value = avatar.isValid()
                    }
                }
            }
        }
    
    fun updateName(name: String) {
        _avatarState.value = _avatarState.value.copy(name = name)
    }
    
    fun updatePronouns(pronouns: String) {
        _avatarState.value = _avatarState.value.copy(pronouns = pronouns)
    }
    
    fun updateStyle(style: String) {
        _avatarState.value = _avatarState.value.copy(style = style)
    }
    
    fun updateBodyType(bodyType: String) {
        _avatarState.value = _avatarState.value.copy(bodyType = bodyType)
    }
    
    fun updateFaceType(faceType: String) {
        _avatarState.value = _avatarState.value.copy(faceType = faceType)
    }
    
    fun updateHair(hair: String) {
        _avatarState.value = _avatarState.value.copy(hair = hair)
    }
    
    fun updateSkinTone(skinTone: String) {
        _avatarState.value = _avatarState.value.copy(skinTone = skinTone)
    }
    
    fun saveAvatar(
        userName: String,
        companionType: String,
        onSuccess: () -> Unit
    ) {
        viewModelScope.launch {
            val avatar = _avatarState.value
            if (avatar.isValid()) {
                // Salvar avatar
                avatarRepository.saveAvatar(avatar)
                
                // Inicializar memória emocional
                memoryRepository.initializeMemory(
                    userName = userName,
                    avatarName = avatar.name,
                    companionType = companionType
                )
                
                onSuccess()
            }
        }
    }
}

