package dev.quochung2003.techonlineshopbackend.controller

import dev.quochung2003.techonlineshopbackend.constant.Route
import dev.quochung2003.techonlineshopbackend.service.BannerService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.service.annotation.GetExchange

@Tag(name = "Banner")
@RestController
@RequestMapping(Route.BANNER_API)
class BannerController(
    private val service: BannerService
) {

    @GetExchange
    fun getAll() = service.getAllInstances()
}