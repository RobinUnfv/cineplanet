package com.robin.msvc_cliente.service;

import com.robin.msvc_cliente.dto.ClienteRequest;
import com.robin.msvc_cliente.dto.ClienteResponse;
import com.robin.msvc_cliente.entity.Cliente;

import java.util.List;
import java.util.UUID;

public interface IClienteServi {

    List<ClienteResponse> listarClientes();
    Cliente guardarCliente(ClienteRequest request);
    void eliminarCliente(UUID id);

}
