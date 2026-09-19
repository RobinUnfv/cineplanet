package com.robin.msvc_historial.handler;

import com.robin.msvc_historial.dto.ErrorResp;
import com.robin.msvc_historial.exception.EstadoIncorrecto;
import com.robin.msvc_historial.exception.SocioNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * SocioNotFoundException → 404 NOT FOUND
     */
    @ExceptionHandler(SocioNotFoundException.class)
    public ResponseEntity<ErrorResp> handleSocioNotFound(
            SocioNotFoundException ex,
            HttpServletRequest request
    ) {

        ErrorResp response = ErrorResp.builder()
                .timestamp(LocalDateTime.now())
                .status(NOT_FOUND.value())
                .error("Not Found")
                .codigo("SOCIO_NOT_FOUND")
                .mensaje(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(NOT_FOUND).body(response);
    }

    @ExceptionHandler(EstadoIncorrecto.class)
    public ResponseEntity<ErrorResp> handleEstadoIncorrecto(
            EstadoIncorrecto ex,
            HttpServletRequest request
    ) {

        ErrorResp response = ErrorResp.builder()
                .timestamp(LocalDateTime.now())
                .status(NOT_FOUND.value())
                .error("Not Found")
                .codigo("ESTADO_NOT_VALIDO")
                .mensaje(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(NOT_FOUND).body(response);
    }

    /**
     * MethodArgumentNotValidException → 400 BAD REQUEST
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResp> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex
    ) {
        log.warn("[GlobalExceptionHandler] - Validation error");

        var errors = new HashMap<String, String>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            var fieldName = ((FieldError) error).getField();
            var errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return ResponseEntity.status(BAD_REQUEST).body(new ErrorResp(
                LocalDateTime.now(),
                BAD_REQUEST.value(),
                "Bad Request",
                "VALIDATION_ERROR",
                "Error de validación en los campos de entrada",
                null,
                errors
        ));
    }

    /**
     * Exception → 500 INTERNAL SERVER ERROR
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResp> handleGeneric(
            Exception ex,
            HttpServletRequest request
    ) {
        log.error("[GlobalExceptionHandler] - Unexpected error: ", ex);

        ErrorResp response = ErrorResp.builder()
                .timestamp(LocalDateTime.now())
                .status(INTERNAL_SERVER_ERROR.value())
                .error("Internal Server Error")
                .codigo("INTERNAL_ERROR")
                .mensaje("Ocurrió un error inesperado")
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(response);
    }

}
