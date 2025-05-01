package com.userservice.rabbitmq;

import com.userservice.dto.NotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitMQPublisher {

    private final RabbitTemplate rabbitTemplate;

//    @Value("${rabbitmq.exchange}")
//    private String exchange;
    @Value("${mail.exchange}")
    private String exchange;

//    @Value("${rabbitmq.routing-key}")
//    private String routingKey;
//    @Value("${rabbitmq.routingKey}")
//    private String routingKey;
    @Value("${mail.routingKey}")
    private String routingKey;

    public void sendNotification(NotificationRequest request) {
        rabbitTemplate.convertAndSend(exchange, routingKey, request);
    }
}




//package com.userservice.rabbitmq;
//
//import com.userservice.dto.NotificationRequest;
//import com.userservice.enums.MessageType;
//import org.springframework.amqp.core.AmqpTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class RabbitMQPublisher {
//
//    private final AmqpTemplate amqpTemplate;
//
//    @Autowired
//    public RabbitMQPublisher(AmqpTemplate amqpTemplate) {
//        this.amqpTemplate = amqpTemplate;
//    }
//
//    public void publish(NotificationRequest request) {
//        amqpTemplate.convertAndSend("mail.exchange", "mail.routingKey", request);
//    }
//
////    public void sendMessage(NotificationRequest notificationRequest) {
//////        amqpTemplate.convertAndSend("notification-exchange", "notification.routing.key", notificationRequest);
////        amqpTemplate.convertAndSend("mail.exchange", "mail.routingKey", notificationRequest);
////    }
//
//public void sendResetPasswordEmail(String email, String token) {
//    NotificationRequest request = NotificationRequest.builder()
//            .to(email)
//            .subject("Reset Password")
//            .body("Use this token to reset your password: " + token)
//            .messageType(MessageType.RESET)
//            .build();
//    publish(request);
//}
//
//    public void sendChangePasswordEmail(String email) {
//        NotificationRequest request = NotificationRequest.builder()
//                .to(email)
//                .subject("Password Changed")
//                .body("Your password was successfully changed.")
//                .messageType(MessageType.RESET_SUCCESS)
//                .build();
//        publish(request);
//    }
//
//}



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