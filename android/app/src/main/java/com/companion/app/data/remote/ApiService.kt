package com.companion.app.data.remote

import com.companion.app.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    // Auth
    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<AuthResponse>
    
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>
    
    // Onboarding
    @POST("onboarding/complete")
    suspend fun completeOnboarding(
        @Header("Authorization") token: String,
        @Body request: OnboardingRequest
    ): Response<OnboardingResponse>
    
    // Companion Chat
    @POST("companion/chat")
    suspend fun sendMessage(
        @Header("Authorization") token: String,
        @Body request: ChatRequest
    ): Response<ChatResponse>
    
    @GET("companion/memory")
    suspend fun getMemory(
        @Header("Authorization") token: String
    ): Response<MemoryResponse>
    
    // Avatar
    @PUT("avatar/update")
    suspend fun updateAvatar(
        @Header("Authorization") token: String,
        @Body request: AvatarUpdateRequest
    ): Response<AvatarResponse>
    
    // Report
    @POST("report/create")
    suspend fun createReport(
        @Header("Authorization") token: String,
        @Body request: ReportRequest
    ): Response<ReportResponse>
}


