import java.util.Properties

plugins {
	kotlin("jvm") version "2.1.0"
	kotlin("plugin.spring") version "2.1.0"
	kotlin("plugin.serialization") version "2.1.0"
	id("org.springframework.boot") version "3.4.3"
	id("io.spring.dependency-management") version "1.1.7"
	id("io.sentry.jvm.gradle") version "5.3.0"
//	id("org.graalvm.buildtools.native") version "0.10.4"
}

group = "dev.quochung2003"
version = "0.1.0"

val props = Properties().apply {
	file("credential.properties").inputStream().use { load(it) }
}

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
	implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("io.sentry:sentry-spring-boot-starter-jakarta")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

	// Kotlin Serialization
	implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.0")

	// Swagger (WebFlux)
	implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:2.8.5")

	// Java JWT
	implementation("io.jsonwebtoken:jjwt-api:0.12.6")
	implementation("io.jsonwebtoken:jjwt-jackson:0.12.6")
	implementation("io.jsonwebtoken:jjwt-impl:0.12.6")

	// Liquibase
	implementation("org.liquibase:liquibase-core")

	// Hikari Connection Pool
	implementation("com.zaxxer:HikariCP:6.2.1")

	developmentOnly("org.springframework.boot:spring-boot-devtools")

	runtimeOnly("org.postgresql:postgresql")
	runtimeOnly("org.postgresql:r2dbc-postgresql")

	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test")
	testImplementation("org.springframework.security:spring-security-test")

	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

sentry {
	includeSourceContext = true

	org = "henry-qi" // Đừng quên thay nó !!
	projectName = "online-tech-shop-backend" // Đừng quên thay nó !!
	authToken = props.getProperty("SENTRY_AUTH_TOKEN") // Đừng quên thay nó !!
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
