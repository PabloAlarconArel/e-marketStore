package org.marketstore.inventoryservice.event;

import java.time.Instant;
import java.util.List;

public record OrderCreatedEvent(
        String eventId,
        String orderId,
        String customerId,
        List<OrderItemEvent> items,
        Instant occurredAt
) {
}
