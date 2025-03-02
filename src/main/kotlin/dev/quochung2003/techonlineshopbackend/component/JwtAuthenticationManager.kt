package dev.quochung2003.techonlineshopbackend.component

import org.springframework.context.annotation.Lazy
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class JwtAuthenticationManager(
    @Lazy private val userDetailsService: ReactiveUserDetailsService,
    private val encoder: PasswordEncoder
) : ReactiveAuthenticationManager {

    override fun authenticate(authentication: Authentication?): Mono<Authentication> {
        return Mono.justOrEmpty(authentication)
            .flatMap { auth ->
                val token = auth.credentials.toString()
                val userDetailsMono = userDetailsService.findByUsername(auth.name)

                userDetailsMono.flatMap { userDetails ->
                    if (encoder.matches(token, userDetails.password)) {
                        Mono.just(UsernamePasswordAuthenticationToken(userDetails, token, userDetails.authorities))
                    } else {
                        Mono.empty()
                    }
                }
            }
    }
}