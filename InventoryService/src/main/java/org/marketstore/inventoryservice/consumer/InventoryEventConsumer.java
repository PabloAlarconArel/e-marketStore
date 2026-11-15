package org.marketstore.inventoryservice.consumer;

import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import org.marketstore.inventoryservice.dto.request.StockItemRequestDto;
import org.marketstore.inventoryservice.dto.request.StockReservationRequestDto;
import org.marketstore.inventoryservice.event.InventoryResultEvent;
import org.marketstore.inventoryservice.event.OrderCreatedEvent;
import org.marketstore.inventoryservice.messaging.InventoryEventPublisher;
import org.marketstore.inventoryservice.service.StockReservationService;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class InventoryEventConsumer {

    private final StockReservationService reservationService;
    private final InventoryEventPublisher eventPublisher;

    @SqsListener("markestore-inventory-queue")
    public void consumerOrderCreated(OrderCreatedEvent event){

        StockReservationRequestDto request = new StockReservationRequestDto();

        request.setOrderId(event.orderId());
        request.setItems(
                event.items().stream()
                        .map(item ->{
                            StockItemRequestDto dto = new StockItemRequestDto();

                            dto.setSkuCode(item.skuCode());
                            dto.setQuantity(item.quantity());

                            return dto;
                        })
                        .toList()
        );

        reservationService.reserveItems(request)
                .flatMap(response ->
                        eventPublisher.publish(
                                new InventoryResultEvent(
                                        UUID.randomUUID().toString(),
                                        event.orderId(),
                                         response.getReservationId(),
                                        "RESERVED",
                                        null,
                                        Instant.now()
                                )
                        )
                )
                .block();

    }
}
