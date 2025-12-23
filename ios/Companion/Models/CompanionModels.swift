import Foundation

// Onboarding
struct OnboardingRequest: Codable {
    let companionType: String
    let conversationTone: String
    let avatar: AvatarData
}

struct AvatarData: Codable {
    let avatarName: String
    let avatarStyle: String
    let avatarBodyType: String
    let avatarFaceType: String
    let avatarHair: String
    let avatarSkinTone: String
    let pronouns: String?
}

struct OnboardingResponse: Codable {
    let success: Bool
}

// Chat
struct ChatRequest: Codable {
    let message: String
}

struct ChatResponse: Codable {
    let response: String
    let emotion: String?
    let requiresSupport: Bool?
    let conversationId: String
}

// Memory
struct MemoryResponse: Codable {
    let userName: String?
    let preferences: [String: Any]?
    let recurringThemes: [String: Any]?
    let frequentEmotions: [String: Any]?
    let routine: [String: Any]?
    let lastInteraction: String?
    let interactionCount: Int
}

// Avatar
struct AvatarUpdateRequest: Codable {
    let avatarName: String?
    let avatarStyle: String?
    let avatarBodyType: String?
    let avatarFaceType: String?
    let avatarHair: String?
    let avatarSkinTone: String?
    let pronouns: String?
}

struct AvatarResponse: Codable {
    let id: String
    let userId: String
    let avatarName: String
    let avatarStyle: String
    let avatarBodyType: String
    let avatarFaceType: String
    let avatarHair: String
    let avatarSkinTone: String
    let pronouns: String?
}

