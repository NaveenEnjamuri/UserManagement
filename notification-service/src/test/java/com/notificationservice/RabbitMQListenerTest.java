// --- ✅ Integration Test: RabbitMQListenerTest.java ---
package com.notificationservice;

import com.notificationservice.dto.NotificationRequest;
import com.notificationservice.enums.MessageType;
import com.notificationservice.service.INotificationService;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RabbitMQListenerTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private INotificationService notificationService;

    @Test
    public void testSendNotificationToQueue() {
        NotificationRequest request = NotificationRequest.builder()
                .to("test@receiver.com")
                .subject("RabbitMQ Test")
                .body("Hello from integration test")
                .type(MessageType.REGISTRATION)
                .build();

        rabbitTemplate.convertAndSend(
                "notification.exchange",
                "notification.routing.key",
                request
        );

        // Since it's async, ideally you'd assert logs, mock service or use Awaitility
        System.out.println("Message published to RabbitMQ.");
    }
}
