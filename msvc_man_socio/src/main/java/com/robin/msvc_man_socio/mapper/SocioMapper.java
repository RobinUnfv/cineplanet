package com.robin.msvc_man_socio.mapper;

import com.robin.msvc_man_socio.dto.SocioRequest;
import com.robin.msvc_man_socio.dto.SocioResponse;
import com.robin.msvc_man_socio.entity.Socio;
import org.springframework.stereotype.Component;

@Component
public class SocioMapper {

    public Socio toSocio(SocioRequest socioRequest) {
        // Implement the mapping logic from SocioRequest to Socio entity
        if (socioRequest == null) {
            return null;
        }

        return Socio.builder()
                .dni(socioRequest.dni())
                .nombre(socioRequest.nombre())
                .paterno(socioRequest.paterno())
                .materno(socioRequest.materno())
                .correo(socioRequest.correo())
                .telefono(socioRequest.telefono())
                .stsSocio(socioRequest.stsSocio())
                .tipoMembresia(socioRequest.tipoMembresia())
                .build();
    }

    public SocioResponse toResponse(Socio socio) {
        if (socio == null) {
            return null;
        }

        return new SocioResponse(
                socio.getDni(),
                socio.getNombre(),
                socio.getPaterno(),
                socio.getMaterno(),
                socio.getCorreo(),
                socio.getTelefono(),
                socio.getStsSocio(),
                socio.getTipoMembresia()
        );
    }

    public void updateSocio(Socio socio, SocioRequest socioRequest) {
        if (socio == null || socioRequest == null) {
            return;
        }

        socio.setDni(socioRequest.dni());
        socio.setNombre(socioRequest.nombre());
        socio.setPaterno(socioRequest.paterno());
        socio.setMaterno(socioRequest.materno());
        socio.setCorreo(socioRequest.correo());
        socio.setTelefono(socioRequest.telefono());
        socio.setStsSocio(socioRequest.stsSocio());
        socio.setTipoMembresia(socioRequest.tipoMembresia());

    }


}
