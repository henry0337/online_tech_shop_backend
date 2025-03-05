package dev.quochung2003.techonlineshopbackend.model

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

@Table(name = "users")
data class User(
    @Id var id: Int = 0,
    @Column("display_name") val name: String = "",
    val email: String = "",
    @JsonIgnore val credentialPassword: String = "",
    val avatar: String? = "",
    val roles: MutableSet<Role> = mutableSetOf(Role.USER),
    val enabled: Boolean = true,
    val isAccountExpired: Boolean = false,
    val isAccountLocked: Boolean = false,
    val isCredentialExpired: Boolean = false,
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
        roles.map { SimpleGrantedAuthority(it.name) }.toMutableList()

    override fun getPassword(): String = credentialPassword
    override fun getUsername(): String = email
    override fun isAccountNonExpired(): Boolean = !isAccountExpired
    override fun isAccountNonLocked(): Boolean = !isAccountLocked
    override fun isCredentialsNonExpired(): Boolean = !isCredentialExpired
    override fun isEnabled(): Boolean = enabled
}

enum class Role {
    USER, ADMIN
}