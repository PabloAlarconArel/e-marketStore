package org.marketstore.inventoryservice.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.marketstore.inventoryservice.enums.Unit;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Table("inventory")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Inventory {
    @Id
    private Long id;
    private String skuCode;
    private BigDecimal availableQuantity;
    private BigDecimal reservedQuantity;
    private Unit unit;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;


}
