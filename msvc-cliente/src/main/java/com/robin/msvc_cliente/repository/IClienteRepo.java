package com.robin.msvc_cliente.repository;

import com.robin.msvc_cliente.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IClienteRepo extends JpaRepository<Cliente, UUID> {
}
