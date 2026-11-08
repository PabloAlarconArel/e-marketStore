package org.marketstore.authservice.dto.request;


import jakarta.validation.constraints.NotBlank;

public record ServiceAuthRequestDto (
    @NotBlank String clientId,
    @NotBlank String clientSecret
){}
