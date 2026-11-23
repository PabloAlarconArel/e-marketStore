package org.marketstore.inventoryservice.publisher;

import io.awspring.cloud.sns.core.async.SnsAsyncTemplate;
import lombok.RequiredArgsConstructor;
import org.marketstore.inventoryservice.event.InventoryResultEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class InventoryEventPublisher {

    private final SnsAsyncTemplate snsAsyncTemplate;

    @Value("${app.aws.topics.inventory}")
    private String inventoryTopicArn;

    public Mono<Void> publish(InventoryResultEvent event){
        return Mono.fromFuture(
                snsAsyncTemplate.convertAndSend(
                        inventoryTopicArn,
                        event
                )
        ).then();
    }
}
