package dev.quochung2003.techonlineshopbackend.controller

import dev.quochung2003.techonlineshopbackend.constant.Route
import dev.quochung2003.techonlineshopbackend.service.ProductService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.service.annotation.GetExchange

@Tag(name = "Sản phẩm")
@RestController
@RequestMapping(Route.PRODUCT_API)
class ProductController(
    private val service: ProductService
) {

    @GetExchange
    fun getAll() = service.getAllInstances()
}