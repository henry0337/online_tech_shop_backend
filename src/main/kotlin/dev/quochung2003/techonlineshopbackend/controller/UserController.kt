package dev.quochung2003.techonlineshopbackend.controller

import dev.quochung2003.techonlineshopbackend.constant.Route
import dev.quochung2003.techonlineshopbackend.service.UserService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.service.annotation.GetExchange

@Tag(name = "Người dùng")
@RestController
@RequestMapping(Route.USER_API)
class UserController(
    private val service: UserService
) {
    @GetExchange
    suspend fun getAll() = service.getAllInstances()
}