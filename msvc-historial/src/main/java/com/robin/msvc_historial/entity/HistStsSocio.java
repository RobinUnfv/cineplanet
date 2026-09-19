package com.robin.msvc_historial.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hist_sts_socio")
public class HistStsSocio {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", columnDefinition = "CHAR(36)")
    @JdbcTypeCode(SqlTypes.CHAR)
    private UUID id;

    @NotBlank(message = "El DNI es obligatorio")
    @Column(name = "dni", length = 8, nullable = false)
    private String dni;

    @NotNull(message = "El estado anterior es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(name = "sts_anterior", length = 8, nullable = true)
    private Estado stsAnterior;

    @NotNull(message = "El estado actual es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(name = "sts_actual", length = 8, nullable = true)
    private Estado stsActual;

    @CreationTimestamp
    @Column(name = "fec_cambio", nullable = false, updatable = false)
    private LocalDateTime fecCambio;

}
