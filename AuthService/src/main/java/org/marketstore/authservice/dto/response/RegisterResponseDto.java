package org.marketstore.authservice.dto.response;

import org.marketstore.authservice.enums.Role;

import java.util.Set;

public record RegisterResponseDto(
        String userId,
        String name,
        String email,
        Set<Role> roles
) {}
