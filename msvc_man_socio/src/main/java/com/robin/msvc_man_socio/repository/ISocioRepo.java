package com.robin.msvc_man_socio.repository;

import com.robin.msvc_man_socio.entity.Socio;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ISocioRepo extends MongoRepository<Socio, String> {

    Optional<Socio> findByDni(String dni);
    boolean existsByDni(String dni);
    boolean existsByCorreo(String correo);
    void deleteByDni(String dni);
}
