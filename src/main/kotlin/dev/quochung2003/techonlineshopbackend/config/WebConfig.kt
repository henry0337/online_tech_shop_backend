package dev.quochung2003.techonlineshopbackend.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.config.CorsRegistry
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.config.WebFluxConfigurer

@Configuration
@EnableWebFlux
class WebConfig {

    @Bean
    fun enableCors() = object : WebFluxConfigurer {
        override fun addCorsMappings(registry: CorsRegistry) {
            super.addCorsMappings(registry)

            registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true)
        }
    }
}