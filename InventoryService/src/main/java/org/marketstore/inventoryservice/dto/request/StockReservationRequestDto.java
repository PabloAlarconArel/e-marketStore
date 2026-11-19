package org.marketstore.inventoryservice.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class StockReservationRequestDto {

    @NotBlank(message = "El order id es obligatorio")
    private String orderId;

    @NotEmpty(message = "La orde debe contener al menos un producto")
    private List<@Valid StockItemRequestDto> items;
}
