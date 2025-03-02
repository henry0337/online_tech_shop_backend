package dev.quochung2003.techonlineshopbackend.component

import dev.quochung2003.techonlineshopbackend.service.JwtService
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.stereotype.Component
import org.springframework.web.server.CoWebFilter
import org.springframework.web.server.CoWebFilterChain
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class JwtFilter(
    private val jwtService: JwtService,
    private val userDetailsService: ReactiveUserDetailsService
) : CoWebFilter() {

    override suspend fun filter(
        exchange: ServerWebExchange,
        chain: CoWebFilterChain
    ) {
        val authHeader = exchange.request.headers.getFirst(HttpHeaders.AUTHORIZATION)

        if (authHeader.isNullOrBlank() || !authHeader.startsWith("Bearer "))
            return chain.filter(exchange)


        val token = authHeader.substring(7)
        val username = jwtService.extractUsernameFromToken(token)

        if (username.isBlank())
            return chain.filter(exchange)


        val userDetails = userDetailsService.findByUsername(username).awaitSingleOrNull()
        if (userDetails != null && jwtService.isTokenValid(token, userDetails)) {
            val authentication = UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.authorities
            )

            return chain.filter(
                exchange.mutate()
                    .principal(Mono.just(authentication))
                    .build()
            )
        }

        chain.filter(exchange)
    }
}