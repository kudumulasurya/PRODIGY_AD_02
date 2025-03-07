package com.example.to_do_list.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

// 🔹 Login Request Data Class
data class LoginRequest(
    val email: String,
    val password: String
)

// 🔹 Login Response Data Class
data class LoginResponse(
    val message: String,
    val token: String? // Optional token
)

// 🔹 Register Request Data Class
data class RegisterRequest(
    val username: String,
    val email: String,
    val phone: Long, // Changed to Long
    val password: String
)

// 🔹 Register Response Data Class
data class RegisterResponse(
    val message: String,
    val success: Boolean
)

// Change password request object
data class PasswordRequest (
    val email: String,
    val otp: String,
    val password: String
)

data class PasswordResponse(
    val message: String,
    val success: Boolean
)

// 🔹 Retrofit API Interface
interface ApiService {
    @POST("login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("register") // New Register API added here!
    fun register(@Body request: RegisterRequest): Call<RegisterResponse>

    @PUT("changePassword")
    fun changePassword(@Body request: PasswordRequest): Call<PasswordResponse>
}
