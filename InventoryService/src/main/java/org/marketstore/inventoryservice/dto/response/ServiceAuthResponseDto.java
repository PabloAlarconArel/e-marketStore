package org.marketstore.inventoryservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceAuthResponseDto {
    private String accessToken;
    private String tokenType;
    private long expiresIn;
}
