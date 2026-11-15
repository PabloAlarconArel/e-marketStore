package org.marketstore.inventoryservice.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.marketstore.inventoryservice.dto.request.InventoryCreateRequestDto;
import org.marketstore.inventoryservice.dto.response.InventoryResponseDto;
import org.marketstore.inventoryservice.model.Inventory;

@Mapper(componentModel = "spring")
public interface InventoryMapper {
    @Mapping(source = "quantity", target = "availableQuantity")
    Inventory toInventory(InventoryCreateRequestDto request);

    InventoryResponseDto toResponse(Inventory inventory);
}
