package com.robin.msvc_cliente.dto;

public record ClienteResponse(
        String nombre,
        String paterno,
        String materno,
        String correo,
        String telefono) {
}
