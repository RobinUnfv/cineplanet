package com.robin.msvc_historial.services;

import com.robin.msvc_historial.dto.HistStsSocioRequest;
import com.robin.msvc_historial.dto.HistStsSocioResponse;
import com.robin.msvc_historial.dto.SocioRequest;
import com.robin.msvc_historial.dto.SocioResponse;
import com.robin.msvc_historial.entity.Estado;
import com.robin.msvc_historial.entity.HistStsSocio;
import com.robin.msvc_historial.exception.SocioNotFoundException;
import com.robin.msvc_historial.mapper.HistStsSocioMapper;
import com.robin.msvc_historial.mapper.SocioMapper;
import com.robin.msvc_historial.repository.IHistStsSocioRepo;
import com.robin.msvc_historial.repository.ISocioFeignRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class HistStsSocioServiImple implements IHistStsSocioServi {

    private final IHistStsSocioRepo histStsSocioRepo;
    private final ISocioFeignRepo socioFeignRepo;
    private final HistStsSocioMapper histStsSocioMapper;
    private final SocioMapper socioMapper;

    @Override
    public SocioResponse obtenerSocioDni(String dni) {
        log.info("Iniciando obtenerSocioDni");
        log.info("Obteniendo socio con DNI: {}", dni);
        return this.socioFeignRepo.obtenerSocioDni(dni)
                .orElseThrow(() -> new SocioNotFoundException("Socio no encontrado con DNI: " + dni));
    }

    @Override
    public SocioResponse actualizarSocio(HistStsSocioRequest histStsSocioRequest, SocioResponse socioResponse) {
        log.info("Iniciando actualizarSocio");
        log.info("Obteniendo HistStsSocioRequest: {}", histStsSocioRequest);
        log.info("Obteniendo SocioResponse: {}", socioResponse);
        var socioRequest = this.socioMapper.toSocioRequest(histStsSocioRequest, socioResponse);
        var dni = histStsSocioRequest.dni();
        log.info("Actualizando socio con DNI: {}", dni);
        log.info("Datos del socio a actualizar: {}", socioRequest);
        return this.socioFeignRepo.actualizarSocio(dni, socioRequest)
                .orElseThrow(() -> new RuntimeException("No se pudo actualizar el socio con DNI: " + dni));
    }

    @Transactional
    @Override
    public HistStsSocioResponse registrarCambioEstado(HistStsSocioRequest histStsSocioRequest,
                                                      SocioResponse socioResponse) {

        log.info("Registrando cambio de estado para el socio con DNI: {}", histStsSocioRequest.dni());
        var stsAnterior = socioResponse.stsSocio();
        log.info("Estado anterior: {}", stsAnterior);

        var histStsSocio = this.histStsSocioRepo.save(this.histStsSocioMapper.toHistStsSocio(histStsSocioRequest, stsAnterior));

        return this.histStsSocioMapper.toResponse(histStsSocio, socioResponse);
    }

}
