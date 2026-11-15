package org.marketstore.inventoryservice.service;

import org.marketstore.inventoryservice.dto.request.InventoryCreateRequestDto;
import org.marketstore.inventoryservice.dto.request.InventoryUpdateRequestDto;
import org.marketstore.inventoryservice.dto.response.InventoryResponseDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface InventoryService {

     Mono<InventoryResponseDto>createInventory(InventoryCreateRequestDto request);

     Mono<InventoryResponseDto>updateInventory(Long id, InventoryUpdateRequestDto request);
}
