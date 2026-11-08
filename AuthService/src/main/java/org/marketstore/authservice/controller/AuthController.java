package org.marketstore.authservice.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.marketstore.authservice.dto.request.LoginRequestDto;
import org.marketstore.authservice.dto.request.RegisterRequestDto;
import org.marketstore.authservice.dto.request.ServiceAuthRequestDto;
import org.marketstore.authservice.dto.response.ApiResponse;
import org.marketstore.authservice.dto.response.AuthResponseDto;
import org.marketstore.authservice.dto.response.RegisterResponseDto;
import org.marketstore.authservice.dto.response.ServiceAuthResponseDto;
import org.marketstore.authservice.service.AuthService;
import org.marketstore.authservice.service.ServiceAuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor

public class AuthController {

    private final AuthService authService;
    private final ServiceAuthenticationService serviceAuthenticationService;

    @GetMapping("/")
    public String home(){
        return "Hello, Home";

    }

    @GetMapping("/secured")
    public String secured(){
        return" Hello, secured ";
    }

    @PostMapping("/register")
    public ResponseEntity <ApiResponse<RegisterResponseDto>> register(@Valid @RequestBody RegisterRequestDto request){
        RegisterResponseDto register = authService.register(request);

        ApiResponse<RegisterResponseDto> response =
                new ApiResponse<>(
                       201,
                       "Registrado correctamente",
                        register
                );
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponseDto>>login(@Valid @RequestBody LoginRequestDto request){
        AuthResponseDto login = authService.login(request);

        ApiResponse<AuthResponseDto> response =
                new ApiResponse<>(
                        200,
                        "Usuario logueado correctamente",
                         login
                );
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin")
    public AuthResponseDto createAdmin(
            @Valid @RequestBody RegisterRequestDto request
    ) {
        return authService.createAdmin(request);
    }

    @PostMapping("/service-token")
    public ResponseEntity<ServiceAuthResponseDto>authenticateService(@Valid @RequestBody ServiceAuthRequestDto request) {

        String token = serviceAuthenticationService.authenticate(
                request.clientId(),
                request.clientSecret()
        );

        ServiceAuthResponseDto response = new ServiceAuthResponseDto(
                token,
                "Bearer",
                300
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);

    }
}
