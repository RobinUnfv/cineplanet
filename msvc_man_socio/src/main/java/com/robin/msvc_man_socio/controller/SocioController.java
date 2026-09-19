package com.robin.msvc_man_socio.controller;

import com.robin.msvc_man_socio.dto.SocioRequest;
import com.robin.msvc_man_socio.dto.SocioResponse;
import com.robin.msvc_man_socio.services.ISocioServi;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/socio")
@RequiredArgsConstructor
public class SocioController {

    private final ISocioServi socioService;

    @GetMapping
    public ResponseEntity<List<SocioResponse>> listaSocios() {
        return ResponseEntity.ok(socioService.listarSocios());
    }

    @GetMapping("/dni/{dni}")
    public ResponseEntity<SocioResponse> obtenerSocioDni(
            @PathVariable
            @NotBlank(message = "El DNI no puede estar vacío")
            @NotNull(message = "El DNI no puede ser nulo")
            @Pattern(regexp = "^\\d{1,8}$", message = "El DNI debe contener solo números y tener máximo 8 dígitos")
            @Size(min = 1, max = 8, message = "El DNI debe tener entre 1 y 8 caracteres")
            String dni) {
       return ResponseEntity.ok(socioService.obtenerSocioPorDni(dni));
    }

    @PostMapping
    public ResponseEntity<SocioResponse> crearSocio(@RequestBody @Valid SocioRequest socioRequest) {
        log.info("[SocioController] - crearSocio: SocioRequest {}", socioRequest);
        return ResponseEntity.ok(socioService.crearSocio(socioRequest));
    }

    @PutMapping("/dni/{dni}")
    public ResponseEntity<SocioResponse> actualizarSocio(@PathVariable String dni,
                                                         @RequestBody @Valid SocioRequest socioRequest) {
        log.info("[SocioController] - actualizarSocio: dni {}", dni);
        log.info("[SocioController] - actualizarSocio: SocioRequest {}", socioRequest);
        return ResponseEntity.ok(socioService.actualizarSocio(dni, socioRequest));
    }

    @DeleteMapping("/dni/{dni}")
    public ResponseEntity<Void> eliminarSocio(@PathVariable String dni) {
        socioService.eliminarSocio(dni);
        return ResponseEntity.noContent().build();
    }

}
