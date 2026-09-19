package com.robin.msvc_historial.repository;

import com.robin.msvc_historial.entity.HistStsSocio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IHistStsSocioRepo extends JpaRepository<HistStsSocio, UUID> {
    List<HistStsSocio> findByDni(String dni);
}
