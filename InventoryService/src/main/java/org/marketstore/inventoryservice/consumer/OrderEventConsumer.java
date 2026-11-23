package org.marketstore.inventoryservice.consumer;

import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.marketstore.inventoryservice.dto.request.StockItemRequestDto;
import org.marketstore.inventoryservice.dto.request.StockReservationRequestDto;
import org.marketstore.inventoryservice.event.InventoryResultEvent;
import org.marketstore.inventoryservice.event.OrderCreatedEvent;
import org.marketstore.inventoryservice.exception.ReservationRejectedException;
import org.marketstore.inventoryservice.publisher.InventoryEventPublisher;
import org.marketstore.inventoryservice.service.StockReservationService;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderEventConsumer {

    private final StockReservationService reservationService;
    private final InventoryEventPublisher inventoryEventPublisher;

    @SqsListener("${app.aws.queues.inventory}")
    public  void consumerOrderCreated(OrderCreatedEvent event){

        log.info("OrderCreated recibido. OrderId: {}", event.orderId());

        processOrder(event).block();
    }

    private Mono<Void> processOrder(OrderCreatedEvent event){

        List<StockItemRequestDto> items = event.items()
                .stream()
                .map(item -> new StockItemRequestDto(
                        item.skuCode(),
                        item.quantity()
                ))
                .toList();

        StockReservationRequestDto request= new StockReservationRequestDto(event.orderId(), items);

        return reservationService.reserveItems(request)
                .flatMap(reservation ->
                    inventoryEventPublisher.publish(
                    new InventoryResultEvent(
                            UUID.randomUUID().toString(),
                            event.orderId(),
                            reservation.getReservationId(),
                            "RESERVED",
                            null,
                            Instant.now()
                    )
                ))
                .onErrorResume(ReservationRejectedException.class, error ->
                    inventoryEventPublisher.publish(
                            new InventoryResultEvent(
                                    UUID.randomUUID().toString(),
                                    event.orderId(),
                                    null,
                                    "REJECTED",
                                    error.getMessage(),
                                    Instant.now()
                            )
                    )
                );
    }
}
