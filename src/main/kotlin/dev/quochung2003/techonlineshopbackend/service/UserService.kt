package dev.quochung2003.techonlineshopbackend.service

import dev.quochung2003.techonlineshopbackend.model.User
import dev.quochung2003.techonlineshopbackend.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val repository: UserRepository
) {
    fun getAllInstances() = repository.findAll()
    suspend fun save(user: User) = repository.save(user)
}