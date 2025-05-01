package com.notificationservice.rabbitmq;

import com.notificationservice.config.NotificationRabbitConfig;
import com.notificationservice.dto.NotificationRequest;
import com.notificationservice.service.INotificationService;
import com.sun.istack.logging.Logger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class RabbitMQListener {

//    @Autowired
//    private INotificationService notificationService;
//    private Logger log;

//    @RabbitListener(queues = "${rabbitmq.queue}")
//    public void handleNotification(NotificationRequest request) {
//        notificationService.sendEmail(request);
//    }

//    @RabbitListener(queues = "mail.queue")
//    public void consume(NotificationRequest request) {
//        notificationService.sendEmail(request);
//    }

    private final INotificationService notificationService;

    @RabbitListener(queues = NotificationRabbitConfig.QUEUE_NAME)
    public void receiveNotification(NotificationRequest request) {
        log.info("📩 Notification received: {}", request);
//        log.info("📩 Notification received: {}");
        System.out.println(request);
        notificationService.sendEmail(request);
    }

}
