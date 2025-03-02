package dev.quochung2003.techonlineshopbackend.service

import dev.quochung2003.techonlineshopbackend.dto.AuthResponse
import dev.quochung2003.techonlineshopbackend.dto.ChangePasswordRequest
import dev.quochung2003.techonlineshopbackend.dto.LoginRequest
import dev.quochung2003.techonlineshopbackend.dto.RegisterRequest
import dev.quochung2003.techonlineshopbackend.model.User
import dev.quochung2003.techonlineshopbackend.repository.UserRepository
import dev.quochung2003.techonlineshopbackend.util.toUserResponse
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val repository: UserRepository,
    private val encoder: PasswordEncoder,
    private val manager: ReactiveAuthenticationManager,
    private val service: JwtService
) {

    suspend fun register(request: RegisterRequest): User =
        request.toUserResponse().let {
            repository.save(it)
        }


    suspend fun login(request: LoginRequest): AuthResponse {
        manager.authenticate(UsernamePasswordAuthenticationToken(request.email, request.password))

        val user = repository.findByEmail(request.email)
        return if (user != null) {
            AuthResponse(
                token = service.generateToken(user),
                refreshToken = service.generateRefreshToken(HashMap(), user)
            )
        } else {
            AuthResponse(
                token = "",
                refreshToken = ""
            )
        }
    }

    suspend fun changePassword(request: ChangePasswordRequest): AuthResponse? {
        val currentUser = repository.findByEmail(request.email)

        if (currentUser == null ||
            request.repeatPassword != request.newPassword ||
            encoder.matches(request.newPassword, currentUser.credentialPassword) ||
            request.oldPassword != currentUser.credentialPassword
        ) return null

        repository.save(
            currentUser.copy(
                credentialPassword = encoder.encode(request.newPassword)
            )
        )

        val token = service.generateToken(currentUser)
        val refreshToken = service.generateRefreshToken(HashMap(), currentUser)
        return AuthResponse(
            token = token,
            refreshToken = refreshToken
        )
    }
}