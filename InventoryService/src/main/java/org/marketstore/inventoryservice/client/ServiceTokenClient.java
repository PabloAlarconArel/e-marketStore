package org.marketstore.inventoryservice.client;

import org.marketstore.inventoryservice.dto.request.ServiceAuthRequestDto;
import org.marketstore.inventoryservice.dto.response.ServiceAuthResponseDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ServiceTokenClient {

    private final WebClient authWebClient;

    @Value("${services.clients.auth.client-id}")
    private String clientId;

    @Value("${services.clients.auth.client-secret}")
    private String clientSecret;

    public ServiceTokenClient(
            @Qualifier("authWebClient") WebClient authWebClient) {
        this.authWebClient = authWebClient;
    }

    public Mono<ServiceAuthResponseDto> getServiceToken() {

        ServiceAuthRequestDto request = new ServiceAuthRequestDto(clientId, clientSecret);

        return authWebClient
                .post()
                .uri("/api/auth/service-token")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(ServiceAuthResponseDto.class);
    }
}
