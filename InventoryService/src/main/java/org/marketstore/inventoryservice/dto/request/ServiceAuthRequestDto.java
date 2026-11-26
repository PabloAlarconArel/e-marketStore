package org.marketstore.inventoryservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceAuthRequestDto {

    private String clientId;
    private String clientSecret;

}