package org.marketstore.inventoryservice.dto.request;


import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.marketstore.inventoryservice.enums.Unit;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class InventoryCreateRequestDto {
    @NotBlank(message = "El código del producto es obligatorio")
    private String skuCode;

    @NotNull(message = "La cantidad es obligatoria")
    @DecimalMin(value = "0.01", message = "La cantidad debe ser mayor que cero")
    private BigDecimal quantity;

    @NotNull(message = "La unidad de venta es obligatoria")
    private Unit unit;



}
