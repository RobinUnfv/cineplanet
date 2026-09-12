package com.robin.msvc_cliente.mapper;


import com.robin.msvc_cliente.dto.ClienteRequest;
import com.robin.msvc_cliente.dto.ClienteResponse;
import com.robin.msvc_cliente.entity.Cliente;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ClienteMapper {

    public Cliente toEntity(ClienteRequest request) {

        return Cliente.builder()
               //.id(UUID.randomUUID())
                .nombre(request.nombre())
                .paterno(request.paterno())
                .materno(request.materno())
                .correo(request.correo())
                .telefono(request.telefono())
                .fechaIngreso(LocalDateTime.now())
                .activo(true)
                .build();
    }

    public ClienteResponse toResponse(Cliente cliente) {
        return new ClienteResponse(
                cliente.getNombre(),
                cliente.getPaterno(),
                cliente.getMaterno(),
                cliente.getCorreo(),
                cliente.getTelefono()
        );
    }


}
