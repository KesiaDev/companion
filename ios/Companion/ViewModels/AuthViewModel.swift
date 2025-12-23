import Foundation
import SwiftUI

@MainActor
class AuthViewModel: ObservableObject {
    @Published var isAuthenticated = false
    @Published var isLoading = false
    @Published var errorMessage: String?
    @Published var currentUser: User?
    
    private let apiService = APIService.shared
    private let storageService = StorageService.shared
    
    init() {
        checkAuthentication()
    }
    
    func checkAuthentication() {
        if let token = storageService.getToken(), !token.isEmpty {
            isAuthenticated = true
        }
    }
    
    func register(email: String, password: String, name: String?, age: Int?) async {
        isLoading = true
        errorMessage = nil
        
        do {
            let request = RegisterRequest(email: email, password: password, name: name, age: age)
            let response = try await apiService.register(request)
            
            storageService.saveToken(response.token)
            storageService.saveUserId(response.user.id)
            currentUser = response.user
            isAuthenticated = true
        } catch {
            errorMessage = "Erro ao registrar: \(error.localizedDescription)"
        }
        
        isLoading = false
    }
    
    func login(email: String, password: String) async {
        isLoading = true
        errorMessage = nil
        
        do {
            let request = LoginRequest(email: email, password: password)
            let response = try await apiService.login(request)
            
            storageService.saveToken(response.token)
            storageService.saveUserId(response.user.id)
            currentUser = response.user
            isAuthenticated = true
        } catch {
            errorMessage = "Erro ao fazer login: \(error.localizedDescription)"
        }
        
        isLoading = false
    }
    
    func logout() {
        storageService.clearAll()
        isAuthenticated = false
        currentUser = nil
    }
}

