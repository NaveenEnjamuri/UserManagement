package com.notificationservice.service.impl;

import com.notificationservice.dto.NotificationRequest;
import com.notificationservice.service.INotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationServiceImpl implements INotificationService {

    private final JavaMailSender mailSender;

    @Override
    public void sendEmail(NotificationRequest request) {
        log.info("📧 Sending email to: {}", request.getTo());

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(request.getTo());
        message.setSubject(request.getSubject());
        message.setText(request.getBody());

        try {
            mailSender.send(message);
            log.info("✅ Email sent successfully to: {}", request.getTo());
        } catch (Exception e) {
            log.error("❌ Failed to send email to {}: {}", request.getTo(), e.getMessage());
        }
    }
}
//@Service
//public class NotificationServiceImpl implements INotificationService {
//
//    @Autowired
//    private JavaMailSender mailSender;
//
//    @Override
//    public void sendEmail(NotificationRequest request) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(request.getTo());
//        message.setSubject(request.getSubject());
//        message.setText(request.getBody());
//        mailSender.send(message);
//
//    }
//}
