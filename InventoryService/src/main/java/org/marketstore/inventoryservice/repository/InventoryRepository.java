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

