package com.robin.msvc_man_socio.dto;

import com.robin.msvc_man_socio.entity.Estado;
import com.robin.msvc_man_socio.entity.Membresia;

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
