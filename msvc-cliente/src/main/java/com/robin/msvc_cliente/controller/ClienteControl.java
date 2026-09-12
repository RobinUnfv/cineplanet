package com.robin.msvc_cliente.controller;

import com.robin.msvc_cliente.dto.ClienteRequest;
import com.robin.msvc_cliente.dto.ClienteResponse;
import com.robin.msvc_cliente.entity.Cliente;
import com.robin.msvc_cliente.service.IClienteServi;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cliente")
public class ClienteControl {

    private final IClienteServi clienteServi;

    @GetMapping("/clientes")
    private ResponseEntity<List<ClienteResponse>> listarClientes() {
        return ResponseEntity.ok(this.clienteServi.listarClientes());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private ResponseEntity<Cliente> registrarCliente(@RequestBody @Valid ClienteRequest request) {
        log.info("[ClienteController] - registrarCliente: {}", request);
        return ResponseEntity.ok(this.clienteServi.guardarCliente(request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> eliminarCliente(@PathVariable("id") UUID id) {
        log.info("[ClienteController] - eliminarCliente: {}", id);
        this.clienteServi.eliminarCliente(id);
        return ResponseEntity.noContent().build();
    }

}
