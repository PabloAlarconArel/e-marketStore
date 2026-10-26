package org.marketstore.authservice.dto.response;

import org.marketstore.authservice.enums.Role;

import java.util.Set;


public record AuthResponseDto (

    String userId,
    String token,
    String name,
    String email,
    Set<Role> roles
    ){}
