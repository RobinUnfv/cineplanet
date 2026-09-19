package com.robin.msvc_historial.services;

import com.robin.msvc_historial.dto.HistStsSocioRequest;
import com.robin.msvc_historial.dto.HistStsSocioResponse;
import com.robin.msvc_historial.dto.SocioRequest;
import com.robin.msvc_historial.dto.SocioResponse;
import com.robin.msvc_historial.entity.Estado;
import com.robin.msvc_historial.entity.HistStsSocio;

public interface IHistStsSocioServi {
    SocioResponse obtenerSocioDni(String dni);
    SocioResponse actualizarSocio(HistStsSocioRequest histStsSocioRequest, SocioResponse socioResponse);
    HistStsSocioResponse registrarCambioEstado(HistStsSocioRequest histStsSocioRequest, SocioResponse socioResponse);
}
