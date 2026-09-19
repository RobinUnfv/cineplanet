package com.robin.msvc_historial.mapper;

import com.robin.msvc_historial.dto.HistStsSocioRequest;
import com.robin.msvc_historial.dto.SocioRequest;
import com.robin.msvc_historial.dto.SocioResponse;
import org.springframework.stereotype.Component;

@Component
public class SocioMapper {

    public SocioRequest toSocioRequest(HistStsSocioRequest histStsSocioRequest,
                                       SocioResponse socioResponse) {
        if (socioResponse == null || histStsSocioRequest == null) {
            return null;
        }

        return new SocioRequest(
                histStsSocioRequest.dni(),
                socioResponse.nombre(),
                socioResponse.paterno(),
                socioResponse.materno(),
                socioResponse.correo(),
                socioResponse.telefono(),
                histStsSocioRequest.estado(),
                socioResponse.tipoMembresia()
        );
    }
}
