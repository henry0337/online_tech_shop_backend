package dev.quochung2003.techonlineshopbackend.dto

data class ChangePasswordRequest(
    val email: String,
    val newPassword: String,
    val repeatPassword: String
)
