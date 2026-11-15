package org.marketstore.inventoryservice.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.marketstore.inventoryservice.enums.Unit;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryUpdateRequestDto {
    private Unit unit;
}
