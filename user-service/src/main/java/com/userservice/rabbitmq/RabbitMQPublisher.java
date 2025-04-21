package com.usermanagement.rabbitmq;

import com.usermanagement.dto.NotificationRequest;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQPublisher {

    private final AmqpTemplate amqpTemplate;

    @Autowired
    public RabbitMQPublisher(AmqpTemplate amqpTemplate) {
        this.amqpTemplate = amqpTemplate;
    }

    public void sendMessage(NotificationRequest notificationRequest) {
//        amqpTemplate.convertAndSend("notification-exchange", "notification.routing.key", notificationRequest);
        amqpTemplate.convertAndSend("mail.exchange", "mail.routingKey", notificationRequest);
    }
}



//Key: The Exchange Name and Routing Key must match on both sides.
//        plaintext
//        Copy
//Edit
//user-service
// └── RabbitTemplate.convertAndSend("mail.exchange", "mail.routingKey", NotificationRequest)
//
//notification-service
// └── Listens to queue: "mail.queue" (bound to exchange: "mail.exchange" with routing key: "mail.routingKey")
//        ✅ No hard coupling — they just need to agree on exchange & routingKey.