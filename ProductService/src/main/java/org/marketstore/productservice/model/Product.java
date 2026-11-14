package org.marketstore.productservice.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.marketstore.productservice.enums.SkuCodeType;
import org.marketstore.productservice.enums.UnitType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;

@Document(collection = "products")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor


public class Product {
    @Id
    private String id;

    private String name;
    private String description;
    private BigDecimal price;
    private UnitType unit;
    private String skuCode;
    private SkuCodeType skuCodeType;
    private String category;

    @CreatedDate
    private Instant createAt;

    @LastModifiedDate
    private Instant updateAt;

}
