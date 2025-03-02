package dev.quochung2003.techonlineshopbackend.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenAPIConfig {

    @Bean
    fun openAPI(): OpenAPI = OpenAPI()
        .openapi(OPENAPI_VERSION)
        .info(Info().apply {
            title = API_TITLE
            version = API_VERSION
            description = API_DESCRIPTION.ifBlank { null }
        })
        .components(Components().addSecuritySchemes(
            "Bearer Token",
            SecurityScheme().apply {
                type = SecurityScheme.Type.HTTP
                bearerFormat = "JWT"
                scheme = "bearer"
            }
        ))

}

private const val OPENAPI_VERSION = "3.1.1"
private const val API_VERSION = "0.1.0"
private const val API_TITLE = "Online Tech Shop"
private const val API_DESCRIPTION = ""