package com.robin.msvc_cliente.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;


import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Cliente", uniqueConstraints = {
        @UniqueConstraint(columnNames = "correo")
})
public class Cliente {

    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(name = "id", columnDefinition = "CHAR(36)")
    private UUID id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre no puede tener más de 50 caracteres")
    @Column(name = "nombre", length = 50, nullable = false)
    private String nombre;

    @NotBlank(message = "El apellido paterno es obligatorio")
    @Size(max = 30, message = "El apellido paterno no puede tener más de 30 caracteres")
    @Column(name = "paterno", length = 30, nullable = false)
    private String paterno;

    @NotBlank(message = "El apellido materno es obligatorio")
    @Size(max = 30, message = "El apellido materno no puede tener más de 30 caracteres")
    @Column(name = "materno", length = 30, nullable = false)
    private String materno;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo debe ser válido")
    @Size(max = 80, message = "El correo no puede tener más de 80 caracteres")
    @Column(name = "correo", length = 80, nullable = false, unique = true)
    private String correo;

    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(regexp = "^[0-9]{9}$", message = "El teléfono debe tener 9 dígitos numéricos")
    @Column(name = "telefono", length = 9, nullable = false)
    private String telefono;

    @CreationTimestamp
    @Column(name = "fechaIngreso", nullable = false, updatable = false)
    private LocalDateTime fechaIngreso;

    @Builder.Default
    @Column(name = "activo", nullable = false)
    private Boolean activo = true;

    public String getNombreCompleto() {
        return this.nombre + " " + this.paterno + " " + this.materno;
    }
}
