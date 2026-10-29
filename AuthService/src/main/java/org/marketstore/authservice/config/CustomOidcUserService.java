package org.marketstore.authservice.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.marketstore.authservice.enums.AuthProvider;
import org.marketstore.authservice.enums.Role;
import org.marketstore.authservice.model.User;
import org.marketstore.authservice.model.UserIdentity;
import org.marketstore.authservice.repository.UserIdentityRepository;
import org.marketstore.authservice.repository.UserRepository;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOidcUserService implements OAuth2UserService<OidcUserRequest,OidcUser> {

    private final UserRepository userRepository;
    private final UserIdentityRepository userIdentityRepository;
    private final OidcUserService oidcUserService = new OidcUserService();


    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        log.info("registration id :{}", registrationId);

        OidcUser oidcUser = oidcUserService.loadUser(userRequest);

        String providerId = oidcUser.getSubject();
        String email = normalizeEmail(oidcUser.getAttribute("email"));
        String name = oidcUser.getAttribute("name");

        log.info(
                "Login OAuth2: proveedor={}, providerId={}, email={}",
                userRequest.getClientRegistration().getRegistrationId(),
                providerId,
                email
        );

        UserIdentity identity = userIdentityRepository
                .findByProviderAndProviderId(
                        AuthProvider.GOOGLE,
                        providerId
                )
                .orElse(null);

        if (identity == null) {
            User user = userRepository.findByEmail(email)
                    .orElseGet(() -> createUser(name, email));

            identity = new UserIdentity();
            identity.setUserId(user.getId());
            identity.setProvider(AuthProvider.GOOGLE);
            identity.setUserId(providerId);
            identity.setPasswordHash(null);

            userIdentityRepository.save(identity);
        }

        return oidcUser;
    }

    private User createUser(String name, String email) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setRoles(Set.of(Role.USER));

        return userRepository.save(user);
    }

    private String normalizeEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException(
                    "Google no proporcionó un correo electrónico"
            );
        }

        return email.trim().toLowerCase();

    }
}
