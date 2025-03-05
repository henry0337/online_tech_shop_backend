package dev.quochung2003.techonlineshopbackend.config

import io.netty.handler.ssl.SslContext
import io.netty.handler.ssl.SslContextBuilder
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory
import org.springframework.boot.web.embedded.netty.NettyServerCustomizer
import org.springframework.boot.web.server.WebServerFactoryCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import reactor.netty.http.server.HttpServer
import java.io.File

@Profile("prod")
@Configuration
class NettyConfig {

    @Bean
    fun nettyContainer() = WebServerFactoryCustomizer<NettyReactiveWebServerFactory> { factory ->
        factory.addServerCustomizers(NettyServerCustomizer { server ->
            val sslContext = createSslContext()
            return@NettyServerCustomizer server.secure { it.sslContext(sslContext) }
        })
    }

    /*
        TODO: Thêm các file sau:
        TODO: - Tệp chuỗi chứng chỉ X.509 ở định dạng PEM
        TODO: - Tệp chứa khóa riêng tư PKCS#8 ở định dạng PEM
     */
    private fun createSslContext(): SslContext {
        val certChainFile = File("path/to/certificate.pem")
        val privateKeyFile = File("path/to/private-key.pem")
        val keyPassword = "your-key-password"

        return SslContextBuilder
            .forServer(certChainFile, privateKeyFile, keyPassword)
            .build()
    }
}