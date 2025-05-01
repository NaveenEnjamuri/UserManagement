package com.notificationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.notificationservice.enums.MessageType;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // ✅ Add this line
public class NotificationRequest implements Serializable {
    private String to;
    private String subject;
    private String body;
    private MessageType type; // Optional: verification, reset-password, etc.
}
