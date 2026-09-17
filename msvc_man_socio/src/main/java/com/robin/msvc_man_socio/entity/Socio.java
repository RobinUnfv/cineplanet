package com.robin.msvc_man_socio.entity;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@Document(collection = "socio")
public class Socio {

    @Id
    private String id;

    @NotBlank(message = "El DNI es obligatorio")
    @Size(min = 8, max = 8, message = "El DNI debe tener 8 dígitos")
    @Pattern(regexp = "^[0-9]{8}$", message = "El DNI debe ser numérico de 8 dígitos")
    @Indexed(unique = true)
    @Field("dni")
    private String dni;

    @NotBlank(message = "El nombre es obligatorio")
    @Field("nombre")
    private String nombre;

    @NotBlank(message = "El apellido paterno es obligatorio")
    @Field("paterno")
    private String paterno;

    @NotBlank(message = "El apellido materno es obligatorio")
    @Field("materno")
    private String materno;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo debe ser válido")
    @Pattern(
            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "El correo no tiene un formato válido"
    )
    @Field("correo")
    private String correo;

    @NotBlank(message = "El teléfono es obligatorio")
    @Size(min = 9, max = 12, message = "El teléfono debe tener entre 9 y 12 caracteres")
    @Field("telefono")
    private String telefono;

    @NotNull(message = "El estado del socio es obligatorio")
    @Field("sts_socio")
    private Estado stsSocio;

    @NotNull(message = "El tipo de membresía es obligatorio")
    @Field("tipo_membresia")
    private Membresia tipoMembresia;

}
