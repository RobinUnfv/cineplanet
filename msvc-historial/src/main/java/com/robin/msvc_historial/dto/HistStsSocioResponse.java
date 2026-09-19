package com.robin.msvc_historial.dto;

import com.robin.msvc_historial.entity.Estado;

import java.time.LocalDateTime;

public record HistStsSocioResponse(
        String dni,
        String socio,
        Estado stsAnterior,
        Estado stsActual,
        LocalDateTime fecCambio
) {
}
