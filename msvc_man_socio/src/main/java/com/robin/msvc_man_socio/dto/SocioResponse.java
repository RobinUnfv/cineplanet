package com.robin.msvc_man_socio.dto;

public record SocioResponse(
        String id,
        String dni,
        String nombre,
        String paterno,
        String materno,
        String correo,
        String telefono,
        String stsSocio,
        String tipoMembresia
) {
}
