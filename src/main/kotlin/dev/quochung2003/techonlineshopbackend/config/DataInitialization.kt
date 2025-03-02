package dev.quochung2003.techonlineshopbackend.config

import dev.quochung2003.techonlineshopbackend.model.Role
import dev.quochung2003.techonlineshopbackend.model.User
import dev.quochung2003.techonlineshopbackend.repository.UserRepository
import kotlinx.coroutines.runBlocking
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
class DataInitialization(
    private val repository: UserRepository
) : CommandLineRunner {

    override fun run(vararg args: String?): Unit = runBlocking {
        val userToSave = mutableSetOf<User>()
        val userRoleAsName = Role.USER.name
        val adminRoleAsName = Role.ADMIN.name

        if (repository.findByRole(userRoleAsName).isEmpty()) {
            val localUserCredential = User().copy(
                name = "Local User",
                email = "localuser@email.com",
                credentialPassword = BCryptPasswordEncoder().encode("user1234"),
                role = mutableSetOf(Role.USER),
            )
            userToSave.add(localUserCredential)
        }

        if (repository.findByRole(adminRoleAsName).isEmpty()) {
            val adminCredential = User().copy(
                name = "Administrator",
                email = "administrator@email.com",
                credentialPassword = BCryptPasswordEncoder().encode("admin123"),
                role = mutableSetOf(Role.ADMIN),
            )
            userToSave.add(adminCredential)
        }

        repository.saveAll(userToSave)
    }
}