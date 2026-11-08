package org.marketstore.authservice.dto.response;

public record ServiceAuthResponseDto(
        String accessToken,
        String tokenType,
        long expiresIn
) {}
