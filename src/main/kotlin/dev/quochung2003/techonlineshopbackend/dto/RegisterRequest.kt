package dev.quochung2003.techonlineshopbackend.dto

import dev.quochung2003.techonlineshopbackend.model.Role

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val avatar: String? = "",
    val role: Role = Role.USER
)
