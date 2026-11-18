package org.marketstore.inventoryservice.repository;

import org.marketstore.inventoryservice.model.StockReservation;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface StockReservationRepository extends ReactiveCrudRepository <StockReservation, UUID> {

    Mono<StockReservation> findByOrderId(String orderId);

    @Modifying
    @Query("""
            UPDATE stock_reservations
            SET status = :newStatus
            WHERE id = :reservationId
               AND status = 'RESERVED'
            """)
    Mono<Integer> updateStatus( UUID reservationId,String newStatus);
}
