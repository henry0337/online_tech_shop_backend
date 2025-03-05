package dev.quochung2003.techonlineshopbackend.controller

import dev.quochung2003.techonlineshopbackend.constant.Route
import dev.quochung2003.techonlineshopbackend.dto.AuthResponse
import dev.quochung2003.techonlineshopbackend.dto.ChangePasswordRequest
import dev.quochung2003.techonlineshopbackend.dto.LoginRequest
import dev.quochung2003.techonlineshopbackend.dto.RegisterRequest
import dev.quochung2003.techonlineshopbackend.model.Role
import dev.quochung2003.techonlineshopbackend.model.User
import dev.quochung2003.techonlineshopbackend.service.AuthService
import dev.quochung2003.techonlineshopbackend.service.JwtService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.service.annotation.GetExchange
import org.springframework.web.service.annotation.PostExchange

@Tag(name = "Xác thực")
@RestController
@RequestMapping(Route.AUTH_API)
class AuthController(
    private val authService: AuthService,
    private val jwtService: JwtService
) {

    @PostExchange(
        url = "/register",
        contentType = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    suspend fun register(
        @RequestPart name: String,
        @RequestPart email: String,
        @RequestPart password: String,
        @RequestPart(required = false) avatar: String?,
        @RequestPart role: String,
    ): User {
        val parsedRole = enumValues<Role>().firstOrNull { it.name.equals(role, ignoreCase = true) } ?: Role.USER

        val request = RegisterRequest(
            name = name,
            email = email,
            password = password,
            avatar = avatar,
            role = parsedRole,
        )
        return authService.register(request)
    }

    @PostExchange(
        url = "/login",
        contentType = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    suspend fun login(
        @RequestPart email: String,
        @RequestPart password: String,
    ): AuthResponse {
        val request = LoginRequest(
            email = email,
            password = password
        )
        return authService.login(request)
    }

    @GetExchange("/userInfo")
    @Operation(security = [SecurityRequirement(name = "Bearer Token")])
    suspend fun obtainUserCredential(@RequestHeader(HttpHeaders.AUTHORIZATION) token: String): User {
        val obtainedToken = if (token.startsWith("Bearer ")) token.substring(7)
        else extractJwtTokenFromSecurityContext()

        return jwtService.obtainUserCredential(obtainedToken)
    }

    @PostExchange(
        url = "/changePassword",
        contentType = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    suspend fun changePassword(
        @RequestPart email: String,
        @RequestPart newPassword: String,
        @RequestPart repeatPassword: String,
    ): AuthResponse? {
        val request = ChangePasswordRequest(
            email = email,
            newPassword = newPassword,
            repeatPassword = repeatPassword
        )

        return authService.changePassword(request)
    }

    private suspend fun extractJwtTokenFromSecurityContext(): String {
        val authentication = ReactiveSecurityContextHolder.getContext().awaitSingleOrNull()?.authentication

        return if (authentication is JwtAuthenticationToken) {
            authentication.token.tokenValue
        } else {
            throw IllegalStateException("SecurityContext không chứa token nào cả!")
        }
    }
}