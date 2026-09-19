package com.robin.msvc_historial.dto;


import com.robin.msvc_historial.entity.Estado;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record HistStsSocioRequest(
        @NotBlank(message = "El DNI es obligatorio")
        String dni,
        @NotNull(message = "El estado es obligatorio")
        Estado estado
) {
}
