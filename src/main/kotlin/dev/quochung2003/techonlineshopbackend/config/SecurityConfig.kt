package dev.quochung2003.techonlineshopbackend.config

import dev.quochung2003.techonlineshopbackend.component.JwtFilter
import dev.quochung2003.techonlineshopbackend.constant.Route
import dev.quochung2003.techonlineshopbackend.model.Role
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.SecurityWebFiltersOrder
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository

@Configuration
@EnableWebFluxSecurity
class SecurityConfig(
    @Lazy private val filter: JwtFilter,
    @Lazy private val authenticationManager: ReactiveAuthenticationManager
) {

    @Bean
    fun securityWebFilterChain(serverHttpSecurity: ServerHttpSecurity): SecurityWebFilterChain {
        serverHttpSecurity
            .csrf { it.disable() }
            .authorizeExchange {
                it.pathMatchers(
                    "${Route.AUTH_API}/**", "/swagger-ui/**",
                    "/v3/api-docs/**", "/api/shutdown",
                    "${Route.USER_API}/**", "${Route.BANNER_API}/**",
                    "${Route.BANNER_API}/**", "${Route.PRODUCT_API}/**"
                ).permitAll()

                it.pathMatchers(
                    "/api/v1/auth/userInfo",
                    "/api/v1/auth/changePassword",
                ).hasAnyRole(Role.USER.name, Role.ADMIN.name)

                it.anyExchange().authenticated()
            }
            .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
            .authenticationManager(authenticationManager)
            .addFilterBefore(filter, SecurityWebFiltersOrder.AUTHENTICATION)

        return serverHttpSecurity.build()
    }

    @Bean
    fun encoder() = BCryptPasswordEncoder()

    @Lazy
    @Bean
    fun reactiveUserDetailsService(encoder: PasswordEncoder): ReactiveUserDetailsService {
        val user = User.withUsername("user")
            .password(encoder.encode("password"))
            .roles("USER")
            .build()
        return MapReactiveUserDetailsService(user)
    }
}