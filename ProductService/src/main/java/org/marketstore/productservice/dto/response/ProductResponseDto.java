package org.marketstore.productservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.marketstore.productservice.enums.SkuCodeType;
import org.marketstore.productservice.enums.UnitType;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ProductResponseDto {

    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private UnitType unit;
    private String skuCode;
    private SkuCodeType skuCodeType;
    private String category;
    private Instant createdAt;
    private Instant updateAt;

}
