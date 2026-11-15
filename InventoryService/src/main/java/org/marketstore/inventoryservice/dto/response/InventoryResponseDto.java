package org.marketstore.inventoryservice.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.marketstore.inventoryservice.enums.Unit;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryResponseDto {
    private String skuCode;
    private BigDecimal availableQuantity;
    private Unit unit;
}
