package org.marketstore.authservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.marketstore.authservice.dto.request.RegisterRequestDto;
import org.marketstore.authservice.dto.response.AuthResponseDto;
import org.marketstore.authservice.dto.response.RegisterResponseDto;
import org.marketstore.authservice.enums.AuthProvider;
import org.marketstore.authservice.enums.Role;
import org.marketstore.authservice.exception.ResourceExistsException;
import org.marketstore.authservice.model.User;
import org.marketstore.authservice.model.UserIdentity;
import org.marketstore.authservice.repository.UserIdentityRepository;
import org.marketstore.authservice.repository.UserRepository;
import org.marketstore.authservice.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor

public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserIdentityRepository userIdentityRepository;
    private final PasswordEncoder passwordEncoder;



    @Override
    public RegisterResponseDto register(RegisterRequestDto request) {
        User user = createUser(request,Role.USER);

        return new RegisterResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRoles()

        );
    }

    private User createUser(RegisterRequestDto request, Role role) {
        String email = normalizeEmail(request.email());

        if (userRepository.findByEmail(email).isPresent()) {
            throw new ResourceExistsException("El email ya está registrado");
        }

        User user = new User();
        user.setName(request.name().trim());
        user.setEmail(email);
        user.setRoles(Set.of(role));

        User savedUser = userRepository.save(user);

        UserIdentity identity = new UserIdentity();
        identity.setUserId(savedUser.getId());
        identity.setProvider(AuthProvider.LOCAL);
        identity.setProviderId(null);
        identity.setPasswordHash(
                passwordEncoder.encode(request.password())
        );

        userIdentityRepository.save(identity);

        return savedUser;
    }

    private String normalizeEmail(String email){

        return email.trim().toLowerCase();
    }

}
