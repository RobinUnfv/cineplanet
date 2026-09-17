package com.robin.msvc_man_socio.controller;

import com.robin.msvc_man_socio.dto.SocioRequest;
import com.robin.msvc_man_socio.dto.SocioResponse;
import com.robin.msvc_man_socio.services.ISocioServi;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/socio")
@RequiredArgsConstructor
public class SocioController {

    private final ISocioServi socioService;

    @GetMapping
    public ResponseEntity<List<SocioResponse>> listaSocios() {
        return ResponseEntity.ok(socioService.listarSocios());
    }

    @GetMapping("/dni/{dni}")
    public ResponseEntity<SocioResponse> obtenerSocioDni(@PathVariable String dni) {
       return ResponseEntity.ok(socioService.obtenerSocioPorDni(dni));
    }

    @PostMapping
    public ResponseEntity<SocioResponse> crearSocio(@RequestBody @Valid SocioRequest socioRequest) {
        return ResponseEntity.ok(socioService.crearSocio(socioRequest));
    }

    @PutMapping("/dni/{dni}")
    public ResponseEntity<SocioResponse> actualizarSocio(@PathVariable String dni,
                                                         @RequestBody @Valid SocioRequest socioRequest) {
        return ResponseEntity.ok(socioService.actualizarSocio(dni, socioRequest));
    }

}
