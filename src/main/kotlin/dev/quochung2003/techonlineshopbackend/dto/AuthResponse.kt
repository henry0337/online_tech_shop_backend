package dev.quochung2003.techonlineshopbackend.dto

data class AuthResponse(
    val token: String,
    val refreshToken: String
)
