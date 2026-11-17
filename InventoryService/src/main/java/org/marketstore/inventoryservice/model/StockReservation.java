package org.marketstore.inventoryservice.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.marketstore.inventoryservice.enums.ReservationStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Table("stock_reservations")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class StockReservation implements Persistable<UUID> {
    @Id
    private UUID id;

    @Column("order_id")
    private String orderId;
    private ReservationStatus status;
    @Column("created_at")
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;

    @Transient
    private boolean newEntity = true;

    @Override
    public boolean isNew() {
        return newEntity;
    }

    public void markPersisted() {
        this.newEntity = false;
    }


}
