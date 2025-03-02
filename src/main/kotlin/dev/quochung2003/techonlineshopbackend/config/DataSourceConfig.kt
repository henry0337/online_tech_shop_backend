package dev.quochung2003.techonlineshopbackend.config

import com.zaxxer.hikari.HikariDataSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import javax.sql.DataSource

@Configuration
class DataSourceConfig {

    // Hàm này là bắt buộc do Liquibase cần JDBC để hoạt động
    @Bean
    @Profile("dev")
    fun configDevelopmentDataSource(): DataSource =
        HikariDataSource().apply {
            jdbcUrl = "jdbc:postgresql://localhost:5432/onlinetechshop"
            username = "postgres"
            password = "1"
        }

    // Hàm này là bắt buộc do Liquibase cần JDBC để hoạt động
    @Bean
    @Profile("prod")
    fun configProductionDataSource(): DataSource =
        HikariDataSource().apply {
            jdbcUrl = "jdbc:postgresql://localhost:5432/onlinetechshop"
            username = "postgres"
            password = "postgres"
        }
}