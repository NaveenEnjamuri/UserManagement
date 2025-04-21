package com.notificationservice.service.impl;

import com.notificationservice.dto.NotificationRequest;
import com.notificationservice.service.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements INotificationService {

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendEmail(NotificationRequest request) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(request.getTo());
        message.setSubject(request.getSubject());
        message.setText(request.getBody());
        mailSender.send(message);

    }
}
