package dev.quochung2003.techonlineshopbackend.config

import io.sentry.Sentry
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleException(e: Exception): String {
        Sentry.captureException(e)
        Sentry.captureMessage(e.localizedMessage)
        return "Có lỗi xảy ra: ${e.message}"
    }
}