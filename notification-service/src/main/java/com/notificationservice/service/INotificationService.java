package com.notificationservice.service;

import com.notificationservice.dto.NotificationRequest;

public interface INotificationService {
    void sendEmail(NotificationRequest request);
}
