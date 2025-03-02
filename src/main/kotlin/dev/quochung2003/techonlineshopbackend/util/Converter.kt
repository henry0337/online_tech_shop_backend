package dev.quochung2003.techonlineshopbackend.util

import dev.quochung2003.techonlineshopbackend.dto.RegisterRequest
import dev.quochung2003.techonlineshopbackend.model.User
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

fun RegisterRequest.toUserResponse() =
    User().copy(
        name = this.name,
        email = this.email,
        credentialPassword = BCryptPasswordEncoder().encode(this.password)
    )