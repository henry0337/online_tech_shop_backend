package dev.quochung2003.techonlineshopbackend.controller

import dev.quochung2003.techonlineshopbackend.constant.Route
import dev.quochung2003.techonlineshopbackend.model.User
import dev.quochung2003.techonlineshopbackend.service.UserService
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.service.annotation.GetExchange
import org.springframework.web.service.annotation.PostExchange

@RestController
@RequestMapping(Route.USER_API)
class UserController(
    private val service: UserService
) {
    @GetExchange
    suspend fun getAll() = service.getAllInstances()

    @PostExchange
    suspend fun save(@RequestBody user: User) = service.save(user)
}