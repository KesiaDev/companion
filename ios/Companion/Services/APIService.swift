import Foundation
import Alamofire

class APIService {
    static let shared = APIService()
    
    // ⚠️ CONFIGURE AQUI A URL DO SEU SERVIDOR
    // Para simulador: http://localhost:3001/api/
    // Para dispositivo físico: http://SEU_IP_LOCAL:3001/api/
    private let baseURL = "http://localhost:3001/api/"
    
    private init() {}
    
    // MARK: - Auth
    
    func register(_ request: RegisterRequest) async throws -> AuthResponse {
        return try await withCheckedThrowingContinuation { continuation in
            AF.request(
                baseURL + "auth/register",
                method: .post,
                parameters: request,
                encoder: JSONParameterEncoder.default
            )
            .validate()
            .responseDecodable(of: AuthResponse.self) { response in
                switch response.result {
                case .success(let authResponse):
                    continuation.resume(returning: authResponse)
                case .failure(let error):
                    continuation.resume(throwing: error)
                }
            }
        }
    }
    
    func login(_ request: LoginRequest) async throws -> AuthResponse {
        return try await withCheckedThrowingContinuation { continuation in
            AF.request(
                baseURL + "auth/login",
                method: .post,
                parameters: request,
                encoder: JSONParameterEncoder.default
            )
            .validate()
            .responseDecodable(of: AuthResponse.self) { response in
                switch response.result {
                case .success(let authResponse):
                    continuation.resume(returning: authResponse)
                case .failure(let error):
                    continuation.resume(throwing: error)
                }
            }
        }
    }
    
    // MARK: - Onboarding
    
    func completeOnboarding(_ request: OnboardingRequest, token: String) async throws -> OnboardingResponse {
        return try await withCheckedThrowingContinuation { continuation in
            AF.request(
                baseURL + "onboarding/complete",
                method: .post,
                parameters: request,
                encoder: JSONParameterEncoder.default,
                headers: ["Authorization": "Bearer \(token)"]
            )
            .validate()
            .responseDecodable(of: OnboardingResponse.self) { response in
                switch response.result {
                case .success(let response):
                    continuation.resume(returning: response)
                case .failure(let error):
                    continuation.resume(throwing: error)
                }
            }
        }
    }
    
    // MARK: - Chat
    
    func sendMessage(_ request: ChatRequest, token: String) async throws -> ChatResponse {
        return try await withCheckedThrowingContinuation { continuation in
            AF.request(
                baseURL + "companion/chat",
                method: .post,
                parameters: request,
                encoder: JSONParameterEncoder.default,
                headers: ["Authorization": "Bearer \(token)"]
            )
            .validate()
            .responseDecodable(of: ChatResponse.self) { response in
                switch response.result {
                case .success(let response):
                    continuation.resume(returning: response)
                case .failure(let error):
                    continuation.resume(throwing: error)
                }
            }
        }
    }
    
    func getMemory(token: String) async throws -> MemoryResponse {
        return try await withCheckedThrowingContinuation { continuation in
            AF.request(
                baseURL + "companion/memory",
                method: .get,
                headers: ["Authorization": "Bearer \(token)"]
            )
            .validate()
            .responseDecodable(of: MemoryResponse.self) { response in
                switch response.result {
                case .success(let response):
                    continuation.resume(returning: response)
                case .failure(let error):
                    continuation.resume(throwing: error)
                }
            }
        }
    }
    
    // MARK: - Avatar
    
    func updateAvatar(_ request: AvatarUpdateRequest, token: String) async throws -> AvatarResponse {
        return try await withCheckedThrowingContinuation { continuation in
            AF.request(
                baseURL + "avatar/update",
                method: .put,
                parameters: request,
                encoder: JSONParameterEncoder.default,
                headers: ["Authorization": "Bearer \(token)"]
            )
            .validate()
            .responseDecodable(of: AvatarResponse.self) { response in
                switch response.result {
                case .success(let response):
                    continuation.resume(returning: response)
                case .failure(let error):
                    continuation.resume(throwing: error)
                }
            }
        }
    }
}

