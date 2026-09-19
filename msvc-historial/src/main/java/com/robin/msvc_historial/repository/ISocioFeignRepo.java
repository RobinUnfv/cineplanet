package com.robin.msvc_historial.repository;

import com.robin.msvc_historial.dto.SocioRequest;
import com.robin.msvc_historial.dto.SocioResponse;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@FeignClient(name = "msvc-man-socio", path = "/api/socio")
public interface ISocioFeignRepo {

    @GetMapping("/dni/{dni}")
    Optional<SocioResponse> obtenerSocioDni(@PathVariable String dni);

    @PutMapping("/dni/{dni}")
    Optional<SocioResponse> actualizarSocio(@PathVariable String dni, @RequestBody @Valid SocioRequest socioRequest);

}
