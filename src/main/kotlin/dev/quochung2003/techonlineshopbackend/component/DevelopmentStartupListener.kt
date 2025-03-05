package dev.quochung2003.techonlineshopbackend.component

import dev.quochung2003.techonlineshopbackend.util.toProcessBuilderCommand
import io.sentry.Sentry
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.io.IOException

@Component
@Profile("dev")
class DevelopmentStartupListener : ApplicationListener<ApplicationReadyEvent> {
    lateinit var builder: ProcessBuilder

    override fun onApplicationEvent(event: ApplicationReadyEvent) {
        val currentOs = System.getProperty("os.name").lowercase()

        when {
            "win" in currentOs -> builder = ProcessBuilder(DevCommand.SWAGGER_WINDOWS_COMMAND.toProcessBuilderCommand())
            "mac" in currentOs -> builder = ProcessBuilder(DevCommand.SWAGGER_MACOS_COMMAND.toProcessBuilderCommand())
            "nix" in currentOs || "nux" in currentOs -> builder =
                ProcessBuilder(DevCommand.SWAGGER_LINUX_COMMAND.toProcessBuilderCommand())
        }
        builder.start()
    }
}

private const val SWAGGER_URL = "http://localhost:8080/swagger-ui/index.html"

private object DevCommand {
    const val SWAGGER_WINDOWS_COMMAND = "cmd /c start $SWAGGER_URL"
    const val SWAGGER_LINUX_COMMAND = "open $SWAGGER_URL"
    const val SWAGGER_MACOS_COMMAND = "xdg-open $SWAGGER_URL"
}

