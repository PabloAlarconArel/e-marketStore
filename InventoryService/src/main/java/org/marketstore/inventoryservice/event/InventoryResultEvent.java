package org.marketstore.inventoryservice.event;

import java.time.Instant;

public record InventoryResultEvent(
     String eventId,
     String orderId,
     String reservationId,
     String status,
     String reason,
     Instant occurredAt
) {}
