package com.robin.msvc_historial.dto;


import com.robin.msvc_historial.entity.Estado;
import jakarta.validation.constraints.NotBlank;

public record HistStsSocioRequest(
        @NotBlank(message = "El DNI es obligatorio")
        String dni,
        @NotBlank(message = "El estado es obligatorio")
        Estado estado
) {
}
