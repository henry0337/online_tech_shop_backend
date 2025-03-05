package dev.quochung2003.techonlineshopbackend.controller

import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.service.annotation.PostExchange
import kotlin.system.exitProcess

@Tag(
    name = "Dừng dự án",
    description = "Dùng để tắt dự án theo chương trình."
)
@RestController
class ShutdownController {

    @PostExchange("/api/shutdown")
    fun exitApplication(): Unit = exitProcess(0)

}