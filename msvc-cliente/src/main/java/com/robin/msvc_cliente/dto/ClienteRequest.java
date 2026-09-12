package com.robin.msvc_cliente.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record ClienteRequest(
        @NotBlank(message = "El nombre es obligatorio")
        @Size(max = 50, message = "El nombre no puede tener más de 50 caracteres")
        String nombre,

        @NotBlank(message = "El apellido paterno es obligatorio")
        @Size(max = 30, message = "El apellido paterno no puede tener más de 30 caracteres")
        String paterno,

        @NotBlank(message = "El apellido materno es obligatorio")
        @Size(max = 30, message = "El apellido materno no puede tener más de 30 caracteres")
        String materno,

        @NotBlank(message = "El correo es obligatorio")
        @Email(message = "El correo debe ser válido")
        @Size(max = 80, message = "El correo no puede tener más de 80 caracteres")
        String correo,

        @NotBlank(message = "El teléfono es obligatorio")
        @Pattern(regexp = "^[0-9]{9}$", message = "El teléfono debe tener 9 dígitos numéricos")
        String telefono
) { }
