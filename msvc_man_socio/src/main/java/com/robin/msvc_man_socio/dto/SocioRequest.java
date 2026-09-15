package com.robin.msvc_man_socio.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.*;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record SocioRequest(

        @NotBlank(message = "El DNI es obligatorio")
        @Size(min = 8, max = 8, message = "El DNI debe tener 8 dígitos")
        String dni,

        @NotBlank(message = "El nombre es obligatorio")
        String nombre,

        @NotBlank(message = "El apellido paterno es obligatorio")
        String paterno,

        @NotBlank(message = "El apellido materno es obligatorio")
        String materno,

        @NotBlank(message = "El correo es obligatorio")
        @Email(message = "El correo debe ser válido")
        @Pattern(
                regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
                message = "El correo no tiene un formato válido"
        )
        String correo,

        @NotBlank(message = "El teléfono es obligatorio")
        @Size(min = 9, max = 12, message = "El teléfono debe tener entre 9 y 12 caracteres")
        String telefono,

        @NotNull(message = "El estado del socio es obligatorio")
        String stsSocio,

        @NotNull(message = "El tipo de membresía es obligatorio")
        String tipoMembresia
        ) { }
