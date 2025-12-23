import Foundation
import SwiftUI

@MainActor
class ChatViewModel: ObservableObject {
    @Published var messages: [ChatMessage] = []
    @Published var isLoading = false
    @Published var requiresSupport = false
    
    private let apiService = APIService.shared
    private let storageService = StorageService.shared
    
    func addInitialMessage() {
        let initialMessage = ChatMessage(
            text: "Oi. Eu sou o Companion. Estou aqui para te acompanhar ao longo do seu dia. Você pode conversar comigo sempre que quiser.",
            isUser: false
        )
        messages.append(initialMessage)
    }
    
    func sendMessage(_ text: String) {
        // Adicionar mensagem do usuário
        let userMessage = ChatMessage(text: text, isUser: true)
        messages.append(userMessage)
        
        isLoading = true
        
        Task {
            guard let token = storageService.getToken() else {
                isLoading = false
                return
            }
            
            do {
                let request = ChatRequest(message: text)
                let response = try await apiService.sendMessage(request, token: token)
                
                let companionMessage = ChatMessage(
                    text: response.response,
                    isUser: false,
                    emotion: response.emotion
                )
                
                messages.append(companionMessage)
                requiresSupport = response.requiresSupport ?? false
            } catch {
                let errorMessage = ChatMessage(
                    text: "Desculpe, estou tendo dificuldades técnicas. Como você está se sentindo hoje?",
                    isUser: false
                )
                messages.append(errorMessage)
            }
            
            isLoading = false
        }
    }
}

