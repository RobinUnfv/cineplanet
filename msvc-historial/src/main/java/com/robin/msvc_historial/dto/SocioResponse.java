package com.robin.msvc_historial.dto;


import com.robin.msvc_historial.entity.Estado;
import com.robin.msvc_historial.entity.Membresia;

public record SocioResponse(
        String dni,
        String nombre,
        String paterno,
        String materno,
        String correo,
        String telefono,
        Estado stsSocio,
        Membresia tipoMembresia,
        int port
) {
}
