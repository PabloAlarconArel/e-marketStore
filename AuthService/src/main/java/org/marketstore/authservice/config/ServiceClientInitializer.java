package org.marketstore.authservice.config;

import lombok.RequiredArgsConstructor;
import org.marketstore.authservice.service.ServiceClientService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class ServiceClientInitializer implements ApplicationRunner{
    private final ServiceClientService serviceClientService;
    private final ServiceClientsProperties serviceClientsProperties;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        serviceClientsProperties.getClients()
                .forEach((name, credentials) ->
                        serviceClientService.registerIfAbsent(
                                credentials.getClientId(),
                                credentials.getClientSecret(),
                                Set.of("SERVICE")
                        ));
    }
}
