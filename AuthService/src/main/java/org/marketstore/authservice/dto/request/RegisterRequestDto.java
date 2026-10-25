package org.marketstore.authservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.marketstore.authservice.enums.Role;

import java.util.Set;


public record RegisterRequestDto (

    @NotBlank(message = "El nombre no puede estar vacío")
    String name,

    @Email(message = "El correo electrónico debe tener un formato válido")
    @NotBlank(message = "El correo electrónico no puede estar vacío")
    String email,

    @Size ( min = 6, message = "La contraseña debe tener por lo menos 6 dígitos")
    @NotBlank(message = "La contraseña es requerida")
    String password,

    Set<Role> roles
    ) {}
