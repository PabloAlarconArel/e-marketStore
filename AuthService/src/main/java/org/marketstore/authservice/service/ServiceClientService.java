package org.marketstore.authservice.service;

import lombok.RequiredArgsConstructor;
import org.marketstore.authservice.model.ServiceClient;
import org.marketstore.authservice.repository.ServiceClientRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ServiceClientService {
    private final ServiceClientRepository repository;
    private final PasswordEncoder passwordEncoder;

    public ServiceClient registerIfAbsent(
            String clientId,
            String clientSecret,
            Set<String> roles
    ) {

        return repository.findByClientId(clientId)
                .orElseGet(() -> {

                    ServiceClient client = new ServiceClient();

                    client.setClientId(clientId);
                    client.setClientSecret(
                            passwordEncoder.encode(clientSecret)
                    );
                    client.setRoles(roles);
                    client.setEnabled(true);

                    return repository.save(client);
                });
    }
}
