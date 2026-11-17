package org.marketstore.inventoryservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.marketstore.inventoryservice.dto.request.InventoryCreateRequestDto;
import org.marketstore.inventoryservice.dto.request.InventoryUpdateRequestDto;
import org.marketstore.inventoryservice.dto.response.InventoryResponseDto;
import org.marketstore.inventoryservice.mapper.InventoryMapper;
import org.marketstore.inventoryservice.model.Inventory;
import org.marketstore.inventoryservice.repository.InventoryRepository;
import org.marketstore.inventoryservice.service.InventoryService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;

    @Override
    public Mono<InventoryResponseDto> getInventoryBySkuCode(String skuCode) {
        return inventoryRepository.findBySkuCode(skuCode)
                .switchIfEmpty(Mono.error(
                        new RuntimeException("Inventario no encontrado")
                ))
                .map(inventoryMapper::toResponse);
    }

    @Override
    public Mono<InventoryResponseDto>createInventory(InventoryCreateRequestDto request){

        return inventoryRepository.existsBySkuCode(request.getSkuCode())
                .flatMap(exists ->{
                    if(exists){
                        return  Mono.error(
                                new RuntimeException("el SKU ya existe")
                        );
                    }
                    Inventory inventory = inventoryMapper.toInventory(request);

                    inventory.setReservedQuantity(BigDecimal.ZERO);
                    inventory.setCreateAt(LocalDateTime.now());
                    inventory.setUpdateAt(LocalDateTime.now());

                    return inventoryRepository.save(inventory);

                })
                .map(inventoryMapper::toResponse);
    }

    @Override
    public Mono<InventoryResponseDto> updateInventory(Long id, InventoryUpdateRequestDto request){
        return inventoryRepository.findById(id)
                .switchIfEmpty(Mono.error(
                        new RuntimeException("Inventario no encontrado")
                ))
                .flatMap(inventory ->{
                    inventory.setUnit(request.getUnit());
                    inventory.setUpdateAt(LocalDateTime.now());

                    return inventoryRepository.save(inventory);
                })
                .map(inventoryMapper::toResponse);
    }
