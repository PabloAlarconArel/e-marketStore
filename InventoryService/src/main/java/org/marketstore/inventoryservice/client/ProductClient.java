package org.marketstore.inventoryservice.client;

import org.marketstore.inventoryservice.dto.response.ProductResponseDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ProductClient {


    private final WebClient productWebclient;
    private final ServiceTokenClient serviceTokenClient;

    public ProductClient(@Qualifier("productWebClient") WebClient productWebclient, ServiceTokenClient serviceTokenClient){
        this.productWebclient = productWebclient;
        this.serviceTokenClient = serviceTokenClient;
    }

    public Mono<ProductResponseDto> getProduct(String skuCode) {
        return serviceTokenClient.getServiceToken()
                .flatMap(auth ->productWebclient
                        .get()
                        .uri("/api/products/code/{skuCode}",skuCode)
                        .headers(headers ->
                                headers.setBearerAuth(auth.getAccessToken()))
                        .retrieve()
                        .bodyToMono(ProductResponseDto.class));
    }
}
