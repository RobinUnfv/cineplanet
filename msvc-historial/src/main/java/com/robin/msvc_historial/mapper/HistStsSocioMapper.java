package com.robin.msvc_historial.mapper;

import com.robin.msvc_historial.dto.HistStsSocioRequest;
import com.robin.msvc_historial.dto.HistStsSocioResponse;
import com.robin.msvc_historial.dto.SocioResponse;
import com.robin.msvc_historial.entity.Estado;
import com.robin.msvc_historial.entity.HistStsSocio;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class HistStsSocioMapper {

    public HistStsSocio toHistStsSocio(HistStsSocioRequest histStsSocioRequest,
                                       Estado stsAnterior) {

        if (histStsSocioRequest == null) {
            return null;
        }

        return HistStsSocio.builder()
                .dni(histStsSocioRequest.dni())
                .stsAnterior(stsAnterior)
                .stsActual(histStsSocioRequest.estado())
                .fecCambio(LocalDateTime.now())
                .build();

    }

    public HistStsSocioResponse toResponse(HistStsSocio histStsSocio, SocioResponse socioResponse) {

        if (histStsSocio == null) {return null;}
        var socio = socioResponse != null ? socioResponse.nombre() + " " + socioResponse.paterno()+ " " + socioResponse.materno() : null;
        return new HistStsSocioResponse(
                histStsSocio.getDni(),
                socio,
                histStsSocio.getStsAnterior(),
                histStsSocio.getStsActual(),
                histStsSocio.getFecCambio()
        );

    }

}