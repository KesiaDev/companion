import Foundation

struct User: Codable, Identifiable {
    let id: String
    let email: String
    let name: String?
}

struct AuthResponse: Codable {
    let user: User
    let token: String
}

struct RegisterRequest: Codable {
    let email: String
    let password: String
    let name: String?
    let age: Int?
}

struct LoginRequest: Codable {
    let email: String
    let password: String
}

