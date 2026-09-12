package com.robin.msvc_cliente.service;

import com.robin.msvc_cliente.dto.ClienteRequest;
import com.robin.msvc_cliente.dto.ClienteResponse;
import com.robin.msvc_cliente.entity.Cliente;
import com.robin.msvc_cliente.mapper.ClienteMapper;
import com.robin.msvc_cliente.repository.IClienteRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClienteServiImpl implements IClienteServi {

    private final IClienteRepo clienteRepo;
    private final ClienteMapper clienteMapper;

    @Override
    public List<ClienteResponse> listarClientes() {
        return this.clienteRepo.findAll().stream().map( clienteMapper::toResponse ).toList();
    }

    @Override
    public Cliente guardarCliente(ClienteRequest request) {
        var cliente = this.clienteMapper.toEntity(request);
        log.info("[ClienteServiImpl] - guardarCliente: {}", cliente);
        return this.clienteRepo.save(cliente);
    }

    @Override
    public void eliminarCliente(UUID id) {
        this.clienteRepo.deleteById(id);
    }
}
