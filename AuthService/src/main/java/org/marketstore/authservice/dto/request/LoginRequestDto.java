package org.marketstore.authservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


public record LoginRequestDto (

    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "El correo electrónico debe tener un formato válido")
    String email,

    @NotBlank(message = "La contraseña es necesaria")
    String password
) {}
