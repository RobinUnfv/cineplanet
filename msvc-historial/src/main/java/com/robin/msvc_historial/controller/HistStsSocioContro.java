package com.robin.msvc_historial.controller;

import com.robin.msvc_historial.dto.HistStsSocioRequest;
import com.robin.msvc_historial.dto.HistStsSocioResponse;
import com.robin.msvc_historial.dto.SocioResponse;
import com.robin.msvc_historial.services.IHistStsSocioServi;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/historial/socio")
@RequiredArgsConstructor
public class HistStsSocioContro {

    private final IHistStsSocioServi histStsSocioServi;

    @PostMapping
    public ResponseEntity<HistStsSocioResponse> actualizarSocioPorDni(@RequestBody @Valid HistStsSocioRequest histStsSocioRequest) {
        log.info("Actualizando Socio por DNI: {}", histStsSocioRequest.dni());
        log.info("Actualizando Socio y estado: {}", histStsSocioRequest.estado());
        var socio = this.histStsSocioServi.obtenerSocioDni(histStsSocioRequest.dni());

        if (socio == null) {
            log.error("Socio con DNI {} no encontrado", histStsSocioRequest.dni());
            return ResponseEntity.notFound().build();
        }

        log.info("Socio encontrado: {}", socio);

        if (histStsSocioRequest.estado().equals(socio.stsSocio())) {
            log.info("El estado del socio con DNI {} ya es {}. No se realizará ninguna actualización.", histStsSocioRequest.dni(), histStsSocioRequest.estado());
            return ResponseEntity.notFound().build();
        }

        var socioActualizado = this.histStsSocioServi.actualizarSocio(histStsSocioRequest, socio);
        log.info("Socio actualizado: {}", socioActualizado);

        if (socioActualizado == null) {
            log.error("Error al actualizar el socio con DNI {}. No se pudo realizar la actualización.", histStsSocioRequest.dni());
            return ResponseEntity.status(500).build();
        }

        var historialResponse = this.histStsSocioServi.registrarCambioEstado(histStsSocioRequest, socioActualizado);
        log.info("Historial de cambio de estado registrado: {}", historialResponse);

        return ResponseEntity.ok(historialResponse);
    }

}
