package dev.quochung2003.techonlineshopbackend.service

import dev.quochung2003.techonlineshopbackend.model.Role
import dev.quochung2003.techonlineshopbackend.model.User
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*
import java.util.function.Function
import javax.crypto.SecretKey

@Service
class JwtService {
    private val now = System.currentTimeMillis()

    fun generateToken(user: User): String {
        val claimCredential = mapOf(
            "id" to user.id,
            "roles" to user.role,
            "name" to user.name,
            "email" to user.email
        )

        val claims = HashMap<String, Any>().apply {
            putAll(claimCredential)
        }

        return Jwts.builder()
            .claims(claims)
            .subject(user.email)
            .issuedAt(Date(now))
            .expiration(Date(now + 24 * 7 * 60 * 60 * 10))
            .signWith(signInKey(), Jwts.SIG.HS256)
            .compact()
    }

    fun generateRefreshToken(
        claims: Map<String, Any>,
        user: User
    ): String = Jwts.builder()
        .claims(claims)
        .subject(user.email)
        .issuedAt(Date(now))
        .expiration(Date(now + 24 * 7 * 60 * 60 * 10))
        .signWith(signInKey(), Jwts.SIG.HS256)
        .compact()


    @Suppress("UNCHECKED_CAST")
    fun obtainUserCredential(token: String): User {
        val currentClaims = extractAllClaims(token)

        return User().copy(
            id = currentClaims["id"] as UUID,
            name = currentClaims["name"] as String,
            email = currentClaims["email"] as String,
            role = currentClaims["roles"] as MutableSet<Role>
        )
    }

    fun isTokenValid(
        token: String,
        userDetails: UserDetails
    ): Boolean {
        val username = extractUsernameFromToken(token)

        return username == userDetails.username && !isTokenExpired(token)
    }

    fun extractUsernameFromToken(token: String): String =
        extractClaims(
            token = token,
            resolver = Claims::getSubject
        )


    private fun signInKey(): SecretKey =
        Keys.hmacShaKeyFor(Decoders.BASE64.decode(RAW_TOKEN))


    private fun isTokenExpired(token: String) =
        extractClaims(
            token = token,
            resolver = Claims::getExpiration
        ).before(Date())


    private fun extractAllClaims(token: String) =
        Jwts.parser()
            .verifyWith(signInKey())
            .build()
            .parseSignedClaims(token)
            .payload


    private fun <T> extractClaims(
        token: String,
        resolver: Function<Claims, T>
    ): T =
        resolver.apply(extractAllClaims(token))
}

private const val RAW_TOKEN = "37405a74de628d7d66e7af2ce4aea13076b382685498876bca5e76cb4a8a73f4"