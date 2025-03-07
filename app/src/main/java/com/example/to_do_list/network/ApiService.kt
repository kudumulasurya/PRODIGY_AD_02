package com.example.to_do_list.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

// Request Data Class
data class LoginRequest(
    val email: String,
    val password: String
)

// Response Data Class
data class LoginResponse(
    val message: String,
    val token: String? // Optional token
)

// Retrofit API Interface
interface ApiService {
    @POST("login") // Replace with your actual API endpoint
    fun login(@Body request: LoginRequest): Call<LoginResponse>
}
