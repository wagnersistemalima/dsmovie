package com.sistemalima.dsmovie.advice

import com.sistemalima.dsmovie.advice.exceptions.ResourceNotFoundException
import com.sistemalima.dsmovie.advice.exceptions.ScoreException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.time.Instant
import javax.servlet.http.HttpServletRequest

@ControllerAdvice
class ResourceExceptionAdvice {

    // metodo para captar exceção de validação

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun validation(e: MethodArgumentNotValidException, request: HttpServletRequest): ResponseEntity<Any> {
        val status = HttpStatus.BAD_REQUEST
        val error = ValidationError(Instant.now(), status.value(), error = "Validation exception", message = e.message, path = request.requestURI)

        for (f: FieldError in e.bindingResult.fieldErrors) {
            error.addError(f.field, f.defaultMessage!!)
        }
        return ResponseEntity.status(status).body(error)
    }

    // metodo para captar exceçoes de recurso não encontrado

    @ExceptionHandler(ResourceNotFoundException::class)
    fun notFound(e: ResourceNotFoundException, request: HttpServletRequest): ResponseEntity<Any> {
        val status = HttpStatus.NOT_FOUND
        val error = ValidationError(Instant.now(), status.value(), "Entity not found", message = e.message!!, path = request.requestURI)

        return ResponseEntity.status(status).body(error)
    }

    @ExceptionHandler(ScoreException::class)
    fun notFound(e: ScoreException, request: HttpServletRequest): ResponseEntity<Any> {
        val status = HttpStatus.BAD_REQUEST
        val error = ValidationError(Instant.now(), status.value(), "Erro validation", message = e.message!!, path = request.requestURI)

        return ResponseEntity.status(status).body(error)
    }
}
