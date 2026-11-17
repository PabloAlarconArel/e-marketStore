package org.marketstore.inventoryservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.marketstore.inventoryservice.enums.Unit;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.util.UUID;

@Table("stock_reservation_items")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class StockReservationItem {

    @Id
    private Long id;
    private UUID reservationId;
    private String skuCode;
    private BigDecimal quantity;
    private Unit unit;
}
