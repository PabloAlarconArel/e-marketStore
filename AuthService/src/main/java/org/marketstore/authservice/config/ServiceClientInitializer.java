package org.marketstore.authservice.config;

import lombok.RequiredArgsConstructor;
import org.marketstore.authservice.service.ServiceClientService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class ServiceClientInitializer implements ApplicationRunner{
    private final ServiceClientService serviceClientService;

    @Value("${service-clients.order.client-id}")
    private String orderClientId;

    @Value("${service-clients.order.client-secret}")
    private String orderClientSecret;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        serviceClientService.registerIfAbsent(
                orderClientId,
                orderClientSecret,
                Set.of("SERVICE")
        );
    }
}
