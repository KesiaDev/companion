import SwiftUI

struct ContentView: View {
    @EnvironmentObject var authViewModel: AuthViewModel
    @State private var isOnboardingComplete = false
    
    var body: some View {
        Group {
            if authViewModel.isAuthenticated && isOnboardingComplete {
                ChatView()
            } else if authViewModel.isAuthenticated {
                OnboardingFlowView()
            } else {
                AuthView()
            }
        }
        .onAppear {
            checkOnboardingStatus()
        }
    }
    
    private func checkOnboardingStatus() {
        // Verificar se onboarding está completo
        // Por enquanto, sempre assume que precisa
        isOnboardingComplete = false
    }
}

