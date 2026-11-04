package org.marketstore.productservice.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.marketstore.productservice.enums.SkuCodeType;
import org.marketstore.productservice.enums.UnitType;

import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ProductRequestDto {
    @NotBlank(message = "El nombre no puede estar vacío")
    private String name;

    private String description;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor que 0")
    private BigDecimal price;

    @NotNull(message = "El stock es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El stock no puede ser negativo")
    private BigDecimal stock;

    @NotNull(message = "La unidad de venta es obligatoria")
    private UnitType unit;

    private String skuCode;

    private SkuCodeType skuCodeType;

    @NotBlank(message = "La categoría es obligatoria")
    private String category;



}
