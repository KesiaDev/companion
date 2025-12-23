import Foundation

class StorageService {
    static let shared = StorageService()
    
    private let tokenKey = "auth_token"
    private let userIdKey = "user_id"
    private let onboardingCompleteKey = "onboarding_complete"
    
    private init() {}
    
    // MARK: - Token
    
    func saveToken(_ token: String) {
        UserDefaults.standard.set(token, forKey: tokenKey)
    }
    
    func getToken() -> String? {
        return UserDefaults.standard.string(forKey: tokenKey)
    }
    
    func removeToken() {
        UserDefaults.standard.removeObject(forKey: tokenKey)
    }
    
    // MARK: - User ID
    
    func saveUserId(_ userId: String) {
        UserDefaults.standard.set(userId, forKey: userIdKey)
    }
    
    func getUserId() -> String? {
        return UserDefaults.standard.string(forKey: userIdKey)
    }
    
    // MARK: - Onboarding
    
    func setOnboardingComplete(_ complete: Bool) {
        UserDefaults.standard.set(complete, forKey: onboardingCompleteKey)
    }
    
    func isOnboardingComplete() -> Bool {
        return UserDefaults.standard.bool(forKey: onboardingCompleteKey)
    }
    
    // MARK: - Clear All
    
    func clearAll() {
        removeToken()
        UserDefaults.standard.removeObject(forKey: userIdKey)
        UserDefaults.standard.removeObject(forKey: onboardingCompleteKey)
    }
}

