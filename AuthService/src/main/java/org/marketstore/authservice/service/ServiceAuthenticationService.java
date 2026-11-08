package org.marketstore.authservice.service;

import lombok.RequiredArgsConstructor;
import org.marketstore.authservice.model.ServiceClient;
import org.marketstore.authservice.repository.ServiceClientRepository;
import org.marketstore.authservice.security.JwtService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServiceAuthenticationService {

    private final ServiceClientRepository serviceClientRepository;
    private final PasswordEncoder passwordEncoder;
    private JwtService jwtService;

    public String authenticate(String clientId,String clientSecret){

        ServiceClient client = serviceClientRepository
                .findByClientId(clientId)
                .orElseThrow(()->
                        new BadCredentialsException("Crendenciales inválidas"));

        if(!client.isEnabled()){
            throw new BadCredentialsException("Cliente deshabilitado");
        }

        if(!passwordEncoder.matches(
                clientSecret,
                client.getClientSecret()
        )){
            throw new BadCredentialsException("Credenciales inválidas");
        }

        return jwtService.generateServiceToken(
                client.getClientId(),
                client.getRoles()
        );
    }
}
