package org.marketstore.inventoryservice.event;

import java.math.BigDecimal;

public record OrderItemEvent(
        String skuCode,
        BigDecimal quantity
) {}
