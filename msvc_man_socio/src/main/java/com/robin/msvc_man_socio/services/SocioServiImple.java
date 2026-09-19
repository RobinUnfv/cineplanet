package com.robin.msvc_man_socio.services;

import com.robin.msvc_man_socio.dto.SocioRequest;
import com.robin.msvc_man_socio.dto.SocioResponse;
import com.robin.msvc_man_socio.entity.Socio;
import com.robin.msvc_man_socio.exception.BusinessException;
import com.robin.msvc_man_socio.exception.SocioDuplicadoException;
import com.robin.msvc_man_socio.exception.SocioNotFoundException;
import com.robin.msvc_man_socio.mapper.SocioMapper;
import com.robin.msvc_man_socio.repository.ISocioRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SocioServiImple implements ISocioServi{

    private final ISocioRepo socioRepo;
    private final SocioMapper socioMapper;

    @Value("${server.port}")
    private int port;

    @Override
    public List<SocioResponse> listarSocios() {
        log.info("[SocioServiImple] - listarSocios - puerto : {}", port);
        log.info("[SocioServiImple] - listarSocios: - List<SocioResponse> {}", socioRepo.findAll());
        return this.socioRepo.findAll()
                .stream()
                .map(socio -> this.socioMapper.toResponse(socio, port))
                .toList();
    }

    @Override
    public SocioResponse obtenerSocioPorDni(String dni) {

        return this.socioRepo.findByDni(dni)
                .map(socio -> this.socioMapper.toResponse(socio, port))
                .orElseThrow( () -> new SocioNotFoundException("Socio no encontrado con el numero de DNI : "+dni) );

    }

    @Transactional
    @Override
    public SocioResponse crearSocio(SocioRequest socioRequest) {
        log.info("[SocioServiImple] - crearSocio: {}", socioRequest);
        validarDuplicados(socioRequest);
        var socio = this.socioRepo.save(this.socioMapper.toSocio(socioRequest));
        return this.socioMapper.toResponse(socio, port);
    }

    @Transactional
    @Override
    public SocioResponse actualizarSocio(String dni, SocioRequest socioRequest) {
        var socioActual = this.socioRepo.findByDni(dni)
                .orElseThrow( () -> new SocioNotFoundException("El numero de DNI no encontrado : "+dni) );
        validarDniOrEmailSocio(socioActual, socioRequest);
        this.socioMapper.updateSocio(socioActual, socioRequest);
        var socioActualizado = this.socioRepo.save(socioActual);
        return this.socioMapper.toResponse(socioActualizado, port);
    }

    @Override
    public void eliminarSocio(String dni) {
        var socio = this.socioRepo.findByDni(dni)
                .orElseThrow( () -> new SocioNotFoundException("El numero de DNI no encontrado : "+dni) );
        this.socioRepo.delete(socio);
    }

    private void validarDniOrEmailSocio(Socio socio, SocioRequest socioRequest) {
        if(!socio.getDni().equals(socioRequest.dni())){
            this.socioRepo.findByDni(socioRequest.dni()).ifPresent(s ->{
               throw new BusinessException("Ya existe otro Socio con el DNI "+socioRequest.dni());
            });
        }
    }

    private void validarDuplicados(SocioRequest request) {
        // Validar DNI duplicado
        if (socioRepo.findByDni(request.dni()).isPresent()) {
            throw new SocioDuplicadoException("DNI", request.dni());
        }
    }

}
