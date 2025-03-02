package dev.quochung2003.techonlineshopbackend.repository

import dev.quochung2003.techonlineshopbackend.model.User
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import java.util.UUID

interface UserRepository : CoroutineCrudRepository<User, UUID> {
    @Query("SELECT * FROM users WHERE :role = ANY(role)")
    suspend fun findByRole(role: String): List<User>
    suspend fun findByEmail(email: String): User?
}