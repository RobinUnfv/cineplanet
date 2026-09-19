package com.robin.msvc_historial.exception;

import lombok.Getter;

import java.util.Map;

@Getter
public class ValidationException extends RuntimeException{
    private final Map<String,String> detalles;

    public ValidationException(String message, Map<String, String> detalles) {
        super(message);
        this.detalles = detalles;
    }

    public ValidationException(String message) {
        super(message);
        this.detalles = null;
    }

}
