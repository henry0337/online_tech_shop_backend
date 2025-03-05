package dev.quochung2003.techonlineshopbackend.component

import dev.quochung2003.techonlineshopbackend.model.Role
import dev.quochung2003.techonlineshopbackend.model.User
import dev.quochung2003.techonlineshopbackend.repository.UserRepository
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component
class DataInitialization(
    private val repository: UserRepository
) : ApplicationListener<ApplicationReadyEvent> {

    override fun onApplicationEvent(event: ApplicationReadyEvent): Unit = runBlocking {

        val userToSave = mutableSetOf<User>()
        val userRoleAsName = Role.USER.name
        val adminRoleAsName = Role.ADMIN.name

        if (repository.findByRole(userRoleAsName).toList().isEmpty()) {
            val localUserCredential = User().copy(
                name = "Local User",
                email = "localuser@email.com",
                credentialPassword = BCryptPasswordEncoder().encode("user1234"),
                roles = mutableSetOf(Role.USER),
            )
            userToSave.add(localUserCredential)
        }

        if (repository.findByRole(adminRoleAsName).toList().isEmpty()) {
            val adminCredential = User().copy(
                name = "Administrator",
                email = "administrator@email.com",
                credentialPassword = BCryptPasswordEncoder().encode("admin123"),
                roles = mutableSetOf(Role.ADMIN),
            )
            userToSave.add(adminCredential)
        }

        repository.saveAll(userToSave).collect { println(it) }
    }
}