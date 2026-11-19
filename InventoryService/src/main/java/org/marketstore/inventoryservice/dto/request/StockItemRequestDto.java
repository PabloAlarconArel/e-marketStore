package org.marketstore.inventoryservice.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockItemRequestDto {

    @NotBlank(message = "El SKU es obligatorio")
    private String skuCode;

    @NotNull(message = "La cantidad es obligatoria")
    @DecimalMin(
            value = "0.0",
            inclusive = false,
            message = "La cantidad debe ser mayor a cero"
    )
    private BigDecimal quantity;

}
