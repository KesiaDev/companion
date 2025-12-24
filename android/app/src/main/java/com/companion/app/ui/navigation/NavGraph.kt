package com.companion.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.companion.app.data.local.AvatarRepository
import com.companion.app.data.local.MemoryRepository
import com.companion.app.data.local.PreferencesManager
import com.companion.app.ui.screens.*
import com.companion.app.ui.viewmodel.FirstConversationViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object AgeVerification : Screen("age_verification")
    object Login : Screen("login")
    object Register : Screen("register")
    object CompanionType : Screen("companion_type")
    object ConversationTone : Screen("conversation_tone")
    object AvatarCreation : Screen("avatar_creation")
    object FirstConversation : Screen("first_conversation")
    object Chat : Screen("chat")
    object Community : Screen("community")
}

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val preferencesManager = PreferencesManager(context)
    
    // Verificar se onboarding está completo
    val isOnboardingComplete = runBlocking {
        preferencesManager.isOnboardingComplete.first()
    }
    
    val startDestination = if (isOnboardingComplete) {
        Screen.Chat.route
    } else {
        Screen.Splash.route
    }
    
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(
                onNavigateToAgeVerification = {
                    navController.navigate(Screen.AgeVerification.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                },
                onNavigateToChat = {
                    navController.navigate(Screen.Chat.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.AgeVerification.route) {
            AgeVerificationScreen(
                onVerified = {
                    navController.navigate(Screen.Login.route)
                }
            )
        }
        
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Chat.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                },
                onOnboardingNeeded = {
                    navController.navigate(Screen.CompanionType.route)
                }
            )
        }
        
        composable(Screen.Register.route) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(Screen.CompanionType.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(Screen.CompanionType.route) {
            CompanionTypeScreen(
                onTypeSelected = { type ->
                    navController.navigate(Screen.ConversationTone.route)
                }
            )
        }
        
        composable(Screen.ConversationTone.route) {
            ConversationToneScreen(
                onToneSelected = { tone ->
                    navController.navigate(Screen.AvatarCreation.route)
                }
            )
        }
        
        composable(Screen.AvatarCreation.route) {
            AvatarCreationScreen(
                onAvatarCreated = {
                    navController.navigate(Screen.FirstConversation.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.FirstConversation.route) {
            // Por enquanto, criar instância local do repositório
            // Futuramente, usar injeção de dependências (Koin)
            val memoryRepository = MemoryRepository()
            val viewModel: FirstConversationViewModel = viewModel(
                factory = object : androidx.lifecycle.ViewModelProvider.Factory {
                    @Suppress("UNCHECKED_CAST")
                    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                        return FirstConversationViewModel(memoryRepository) as T
                    }
                }
            )
            
            FirstConversationScreen(
                viewModel = viewModel,
                onConversationStarted = {
                    navController.navigate(Screen.Chat.route) {
                        popUpTo(Screen.FirstConversation.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.Chat.route) {
            ChatScreen()
        }
        
        composable(Screen.Community.route) {
            CommunityPlaceholderScreen()
        }
    }
}


