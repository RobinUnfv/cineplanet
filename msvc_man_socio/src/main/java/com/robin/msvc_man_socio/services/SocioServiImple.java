package com.robin.msvc_man_socio.services;

import com.robin.msvc_man_socio.dto.SocioRequest;
import com.robin.msvc_man_socio.dto.SocioResponse;
import com.robin.msvc_man_socio.mapper.SocioMapper;
import com.robin.msvc_man_socio.repository.ISocioRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SocioServiImple implements ISocioServi{

    private final ISocioRepo socioRepo;
    private final SocioMapper socioMapper;

    @Override
    public List<SocioResponse> listarSocios() {
        return List.of();
    }

    @Override
    public SocioResponse obtenerSocioPorDni(String dni) {
        return null;
    }

    @Override
    public SocioResponse crearSocio(SocioRequest socioRequest) {
        return null;
    }

    @Override
    public SocioResponse actualizarSocio(String dni, SocioRequest socioRequest) {
        return null;
    }

    @Override
    public void eliminarSocio(String dni) {

    }
}
