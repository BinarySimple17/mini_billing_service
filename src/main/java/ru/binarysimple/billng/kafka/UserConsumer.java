package ru.binarysimple.billng.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import ru.binarysimple.billng.service.AccountService;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserConsumer {
    private final AccountService accountService;

    @KafkaListener(topics = "${app.kafka.topics.user-events:user.events}", groupId = "billing-service")
    public void onOrderEvent(@Payload UserEvent event) {
        log.info("Received user event {}", event.getUsername());
        accountService.processUserEvent(event);
    }
}
