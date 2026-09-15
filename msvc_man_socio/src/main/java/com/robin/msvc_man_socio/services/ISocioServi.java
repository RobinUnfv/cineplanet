package com.robin.msvc_man_socio.services;


import com.robin.msvc_man_socio.dto.SocioRequest;
import com.robin.msvc_man_socio.dto.SocioResponse;

import java.util.List;

public interface ISocioServi {

    List<SocioResponse> listarSocios();

    SocioResponse obtenerSocioPorDni(String dni);

    SocioResponse crearSocio(SocioRequest socioRequest);

    SocioResponse actualizarSocio(String dni, SocioRequest socioRequest);

    void eliminarSocio(String dni);
}
