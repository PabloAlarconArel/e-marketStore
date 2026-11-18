package org.marketstore.inventoryservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.marketstore.inventoryservice.dto.request.StockItemRequestDto;
import org.marketstore.inventoryservice.dto.request.StockReservationRequestDto;
import org.marketstore.inventoryservice.dto.response.StockReservationResponseDto;
import org.marketstore.inventoryservice.enums.ReservationStatus;
import org.marketstore.inventoryservice.enums.Unit;
import org.marketstore.inventoryservice.exception.ReservationRejectedException;
import org.marketstore.inventoryservice.mapper.StockReservationMapper;
import org.marketstore.inventoryservice.model.Inventory;
import org.marketstore.inventoryservice.model.StockReservation;
import org.marketstore.inventoryservice.model.StockReservationItem;
import org.marketstore.inventoryservice.repository.InventoryRepository;
import org.marketstore.inventoryservice.repository.StockReservationItemRepository;
import org.marketstore.inventoryservice.repository.StockReservationRepository;
import org.marketstore.inventoryservice.service.StockReservationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StockReservationServiceImpl implements StockReservationService {

    private final StockReservationRepository reservationRepository;
    private final StockReservationItemRepository itemRepository;
    private final InventoryRepository inventoryRepository;
    private final StockReservationMapper reservationMapper;

    @Override
    @Transactional
    public Mono<StockReservationResponseDto> reserveItems(StockReservationRequestDto request) {
        return Mono.defer(() -> validateRequest(request)
                .then(reservationRepository.findByOrderId(request.getOrderId())
                        .map(existing -> reservationMapper.toResponse(
                                existing,
                                "La orden ya tiene una reserva"
                        ))
                        .switchIfEmpty(Mono.defer(() -> createReservation(request)
                                .flatMap(reservation -> processReservationItems(
                                                reservation,
                                                request.getItems()
                                        )
                                        .thenReturn(reservationMapper.toResponse(
                                                reservation,
                                                "Stock reservado exitosamente"
                                        ))
                                )
                        ))
                )
        );
    }

    private Mono<Void> validateRequest(StockReservationRequestDto request){

        if (request == null) {
            return Mono.error(new IllegalArgumentException("La solicitud es obligatoria"));
        }

        if(request.getOrderId() == null || request.getOrderId().isBlank()){
            return Mono.error(
                    new IllegalArgumentException("El orderId es obligatorio")
            );
        }
        if (request.getItems()== null || request.getItems().isEmpty()){
            return Mono.error(
                    new IllegalArgumentException("La orden no contiene productos")
            );
        }

        if (request.getItems().stream().anyMatch(item -> item == null)) {
            return Mono.error(new IllegalArgumentException("La lista contiene un producto nulo"));
        }

        return Mono.empty();
    }

    private Mono<StockReservation> createReservation(StockReservationRequestDto request){

        StockReservation reservation = new StockReservation();

        reservation.setId(UUID.randomUUID());
        reservation.setOrderId(request.getOrderId());
        reservation.setStatus(ReservationStatus.RESERVED);
        reservation.setCreatedAt(LocalDateTime.now());

        return reservationRepository.save(reservation)
                .doOnNext(StockReservation::markPersisted);
    }

    private Mono<Void> processReservationItems(StockReservation reservation, List<StockItemRequestDto> items){
        return Flux.fromIterable(items)
                .concatMap(item ->
                        reserveSingleItem(reservation.getId(),item)
                )
                .then();
    }

    private Mono<Void> reserveSingleItem(UUID reservationId, StockItemRequestDto item){
        return validateStockItem(item)
                .then(findInventory(item.getSkuCode()))
                .flatMap(inventory ->
                        reserveAvailableStock(item)
                                .then(saveReservationItem(
                                        reservationId,
                                        item,
                                        inventory.getUnit()
                                ))
                );
    }

    private Mono<Void> validateStockItem(StockItemRequestDto item){
        if(item.getSkuCode() == null || item.getSkuCode().isBlank()){
            return Mono.error(
                    new IllegalArgumentException("El SKU es obligatorio")
            );
        }
        if(item.getQuantity() == null || item.getQuantity().compareTo(BigDecimal.ZERO)<= 0){
            return Mono.error(
                    new IllegalArgumentException("La cantidad debe ser mayor a cero")
            );
        }
        return Mono.empty();
    }

    private Mono<Inventory> findInventory(String skuCode){
        return  inventoryRepository.findBySkuCode(skuCode)
                .switchIfEmpty(Mono.error(
                        new ReservationRejectedException(
                                "Producto no encontrado" + skuCode
                        )
                ));
    }

    private Mono<Void> reserveAvailableStock(StockItemRequestDto item){
        return inventoryRepository.reserveStock(
                item.getSkuCode(),
                item.getQuantity()
                )
                .flatMap(rows ->{
                    if(rows == 0){
                        return Mono.error(
                                new ReservationRejectedException(
                                        "Stock insuficiente para el producto: " + item.getSkuCode()
                                )
                        );
                    }
                    return Mono.empty();
                });
    }

    private Mono<Void> saveReservationItem(UUID reservationId, StockItemRequestDto item, Unit unit){
        StockReservationItem reservationItem = new StockReservationItem();

        reservationItem.setReservationId(reservationId);
        reservationItem.setSkuCode(item.getSkuCode());
        reservationItem.setQuantity(item.getQuantity());
        reservationItem.setUnit(unit);

        return itemRepository.save(reservationItem)
                .then();
    }

}
