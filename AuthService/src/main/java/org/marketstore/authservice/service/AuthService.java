package org.marketstore.authservice.service;

import org.marketstore.authservice.dto.request.LoginRequestDto;
import org.marketstore.authservice.dto.request.RegisterRequestDto;
import org.marketstore.authservice.dto.response.AuthResponseDto;
import org.marketstore.authservice.dto.response.RegisterResponseDto;

public interface AuthService {

   RegisterResponseDto register (RegisterRequestDto request);

    AuthResponseDto login (LoginRequestDto request);

    AuthResponseDto createAdmin(RegisterRequestDto request);

}
