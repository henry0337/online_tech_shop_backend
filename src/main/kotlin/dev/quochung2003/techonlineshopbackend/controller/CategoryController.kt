package dev.quochung2003.techonlineshopbackend.controller

import dev.quochung2003.techonlineshopbackend.constant.Route
import dev.quochung2003.techonlineshopbackend.service.CategoryService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.service.annotation.GetExchange

@RestController
@RequestMapping(Route.CATEGORY_API)
class CategoryController(
    private val service: CategoryService
) {

    @GetExchange
    fun getAll() = service.getAllInstances()
}