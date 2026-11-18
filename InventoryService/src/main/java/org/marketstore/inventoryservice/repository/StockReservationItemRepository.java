package org.marketstore.inventoryservice.repository;

import org.marketstore.inventoryservice.model.StockReservationItem;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface StockReservationItemRepository extends ReactiveCrudRepository<StockReservationItem, UUID>{

    Flux<StockReservationItem> findByReservationId(
            UUID reservationId
    );
}
