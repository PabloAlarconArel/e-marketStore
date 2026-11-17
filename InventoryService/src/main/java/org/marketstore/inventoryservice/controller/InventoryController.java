package org.marketstore.inventoryservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.marketstore.inventoryservice.dto.request.InventoryCreateRequestDto;
import org.marketstore.inventoryservice.dto.request.InventoryUpdateRequestDto;
import org.marketstore.inventoryservice.dto.response.InventoryResponseDto;
import org.marketstore.inventoryservice.service.InventoryService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor

public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/{skuCode}")
    public Mono<InventoryResponseDto> getInventoryBySkuCode(@PathVariable String skuCode){
        return inventoryService.getInventoryBySkuCode(skuCode);
    }


    @PostMapping
    public Mono<InventoryResponseDto>createInventory(@Valid @RequestBody InventoryCreateRequestDto request){
        return inventoryService.createInventory(request);
    }

    @PutMapping("/{id}")
    public Mono<InventoryResponseDto> updateInventory(@PathVariable Long id, @RequestBody InventoryUpdateRequestDto request){
        return inventoryService.updateInventory(id,request);
    }




}
