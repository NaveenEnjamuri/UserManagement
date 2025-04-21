package com.notificationservice.rabbitmq;

import com.notificationservice.dto.NotificationRequest;
import com.notificationservice.service.INotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQListener {

    @Autowired
    private INotificationService notificationService;

    @RabbitListener(queues = "${rabbitmq.queue}")
    public void handleNotification(NotificationRequest request) {
        notificationService.sendEmail(request);
    }

//    @RabbitListener(queues = "mail.queue")
//    public void consume(NotificationRequest request) {
//        notificationService.sendEmail(request);
//    }
}
