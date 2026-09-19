package com.robin.msvc_historial.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Map;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResp(
        LocalDateTime timestamp,
        int status,
        String error,
        String codigo,
        String mensaje,
        String path,
        Map<String, String> detalles
) {
}
