package com.robin.msvc_man_socio.handler;

import com.robin.msvc_man_socio.entity.ErrorResp;
import com.robin.msvc_man_socio.exception.BusinessException;
import com.robin.msvc_man_socio.exception.SocioDuplicadoException;
import com.robin.msvc_man_socio.exception.SocioNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@Slf4j
//@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * SocioNotFoundException → 404 NOT FOUND
     */
    @ExceptionHandler(SocioNotFoundException.class)
    public ResponseEntity<ErrorResp> handleSocioNotFound(
            SocioNotFoundException ex,
            HttpServletRequest request
    ) {
        log.warn("[GlobalExceptionHandler] - SocioNotFoundException: {}", ex.getMessage());

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

    /**
     * SocioDuplicadoException → 409 CONFLICT
     */
    @ExceptionHandler(SocioDuplicadoException.class)
    public ResponseEntity<ErrorResp> handleSocioDuplicado(
            SocioDuplicadoException ex,
            HttpServletRequest request
    ) {
        log.warn("[GlobalExceptionHandler] - SocioDuplicadoException: {}", ex.getMessage());

        Map<String, String> detalles = new HashMap<>();
        if (ex.getCampo() != null) {
            detalles.put("campo", ex.getCampo());
            detalles.put("valor", ex.getValor());
        }

        ErrorResp response = ErrorResp.builder()
                .timestamp(LocalDateTime.now())
                .status(CONFLICT.value())
                .error("Conflict")
                .codigo("SOCIO_DUPLICADO")
                .mensaje(ex.getMessage())
                .path(request.getRequestURI())
                .detalles(detalles.isEmpty() ? null : detalles)
                .build();

        return ResponseEntity.status(CONFLICT).body(response);
    }

    /**
     * BusinessException → 409 CONFLICT
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResp> handleBusiness(
            BusinessException ex,
            HttpServletRequest request
    ) {
        log.warn("[GlobalExceptionHandler] - BusinessException: {}", ex.getMessage());

        ErrorResp response = ErrorResp.builder()
                .timestamp(LocalDateTime.now())
                .status(CONFLICT.value())
                .error("Business Error")
                .codigo("BUSINESS_ERROR")
                .mensaje(ex.getMessage())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(CONFLICT).body(response);
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
