package dev.quochung2003.techonlineshopbackend.controller

import dev.quochung2003.techonlineshopbackend.constant.Route
import dev.quochung2003.techonlineshopbackend.dto.AuthResponse
import dev.quochung2003.techonlineshopbackend.dto.ChangePasswordRequest
import dev.quochung2003.techonlineshopbackend.dto.LoginRequest
import dev.quochung2003.techonlineshopbackend.dto.RegisterRequest
import dev.quochung2003.techonlineshopbackend.model.User
import dev.quochung2003.techonlineshopbackend.service.AuthService
import dev.quochung2003.techonlineshopbackend.service.JwtService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.http.HttpHeaders
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.service.annotation.GetExchange
import org.springframework.web.service.annotation.PostExchange

@Tag(name = "Authentication")
@RestController
@RequestMapping(Route.AUTH_API)
class AuthController(
    private val authService: AuthService,
    private val jwtService: JwtService
) {

    @PostExchange("/register")
    suspend fun register(@RequestBody request: RegisterRequest): User =
        authService.register(request)


    @PostExchange("/login")
    suspend fun login(@RequestBody request: LoginRequest): AuthResponse =
        authService.login(request)


    @GetExchange("/userInfo")
    @Operation(security = [SecurityRequirement(name = "Bearer Token")])
    suspend fun obtainUserCredential(@RequestHeader(HttpHeaders.AUTHORIZATION) token: String): User {
        val obtainedToken = if (token.startsWith("Bearer ")) token.substring(7)
        else extractJwtTokenFromSecurityContext()

        return jwtService.obtainUserCredential(obtainedToken)
    }

    @PostExchange("/changePassword")
    suspend fun changePassword(@RequestBody request: ChangePasswordRequest): AuthResponse? =
        authService.changePassword(request)


    private suspend fun extractJwtTokenFromSecurityContext(): String {
        val authentication = ReactiveSecurityContextHolder.getContext().awaitSingleOrNull()?.authentication

        return if (authentication is JwtAuthenticationToken) {
            authentication.token.tokenValue
        } else {
            throw IllegalStateException("SecurityContext không chứa token nào cả!")
        }
    }
}