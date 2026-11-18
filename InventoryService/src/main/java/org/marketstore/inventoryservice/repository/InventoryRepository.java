package org.marketstore.inventoryservice.repository;

import org.marketstore.inventoryservice.model.Inventory;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;


public interface InventoryRepository extends ReactiveCrudRepository<Inventory,Long> {

    Mono<Inventory> findBySkuCode(String skuCode);

    Mono<Boolean> existsBySkuCode(String skuCode);

    @Modifying
    @Query("""
            UPDATE inventory
            SET available_quantity = available_quantity - :quantity,
                reserved_quantity = reserved_quantity + :quantity,
                update_at = CURRENT_TIMESTAMP
            WHERE sku_code = :skuCode
                  AND available_quantity >= :quantity
            """)
    Mono<Integer> reserveStock(String skuCode, BigDecimal quantity);
