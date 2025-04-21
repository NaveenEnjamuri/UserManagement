package com.notificationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequest {
    private String to;
    private String subject;
    private String body;
    private String type; // Optional: verification, reset-password, etc.
}
