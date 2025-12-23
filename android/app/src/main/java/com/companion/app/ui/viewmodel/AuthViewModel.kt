package com.companion.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.companion.app.data.local.PreferencesManager
import com.companion.app.data.remote.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val apiService: ApiService,
    private val preferencesManager: PreferencesManager
) : ViewModel() {
    
    sealed class AuthState {
        object Idle : AuthState()
        object Loading : AuthState()
        data class Success(val token: String, val userId: String) : AuthState()
        data class Error(val message: String) : AuthState()
        object NeedsOnboarding : AuthState()
    }
    
    private val _loginState = MutableStateFlow<AuthState>(AuthState.Idle)
    val loginState: StateFlow<AuthState> = _loginState.asStateFlow()
    
    private val _registerState = MutableStateFlow<AuthState>(AuthState.Idle)
    val registerState: StateFlow<AuthState> = _registerState.asStateFlow()
    
    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = AuthState.Loading
            try {
                val response = apiService.login(com.companion.app.data.model.LoginRequest(email, password))
                if (response.isSuccessful && response.body() != null) {
                    val body = response.body()!!
                    preferencesManager.saveToken("Bearer ${body.token}")
                    preferencesManager.saveUserId(body.user.id)
                    
                    // Verificar se precisa de onboarding (verificar se tem avatar)
                    // Por enquanto, sempre assume que precisa se acabou de registrar
                    _loginState.value = AuthState.NeedsOnboarding
                } else {
                    _loginState.value = AuthState.Error(
                        response.errorBody()?.string() ?: "Erro ao fazer login"
                    )
                }
            } catch (e: Exception) {
                _loginState.value = AuthState.Error(e.message ?: "Erro desconhecido")
            }
        }
    }
    
    fun register(email: String, password: String, name: String?, age: Int?) {
        viewModelScope.launch {
            _registerState.value = AuthState.Loading
            try {
                val response = apiService.register(
                    com.companion.app.data.model.RegisterRequest(email, password, name, age)
                )
                if (response.isSuccessful && response.body() != null) {
                    val body = response.body()!!
                    preferencesManager.saveToken("Bearer ${body.token}")
                    preferencesManager.saveUserId(body.user.id)
                    _registerState.value = AuthState.Success(body.token, body.user.id)
                } else {
                    _registerState.value = AuthState.Error(
                        response.errorBody()?.string() ?: "Erro ao cadastrar"
                    )
                }
            } catch (e: Exception) {
                _registerState.value = AuthState.Error(e.message ?: "Erro desconhecido")
            }
        }
    }
}


