package com.companion.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.companion.app.data.local.PreferencesManager
import com.companion.app.data.remote.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OnboardingViewModel(
    private val apiService: ApiService,
    private val preferencesManager: PreferencesManager,
    private val token: String
) : ViewModel() {
    
    sealed class OnboardingState {
        object Idle : OnboardingState()
        object Loading : OnboardingState()
        data class Success(val success: Boolean) : OnboardingState()
        data class Error(val message: String) : OnboardingState()
    }
    
    private val _state = MutableStateFlow<OnboardingState>(OnboardingState.Idle)
    val state: StateFlow<OnboardingState> = _state.asStateFlow()
    
    fun completeOnboarding(
        companionType: String,
        conversationTone: String,
        avatar: com.companion.app.data.model.AvatarData
    ) {
        viewModelScope.launch {
            _state.value = OnboardingState.Loading
            try {
                val response = apiService.completeOnboarding(
                    token,
                    com.companion.app.data.model.OnboardingRequest(
                        companionType,
                        conversationTone,
                        avatar
                    )
                )
                
                if (response.isSuccessful && response.body() != null) {
                    preferencesManager.setOnboardingComplete(true)
                    _state.value = OnboardingState.Success(true)
                } else {
                    _state.value = OnboardingState.Error(
                        response.errorBody()?.string() ?: "Erro ao completar onboarding"
                    )
                }
            } catch (e: Exception) {
                _state.value = OnboardingState.Error(e.message ?: "Erro desconhecido")
            }
        }
    }
}

