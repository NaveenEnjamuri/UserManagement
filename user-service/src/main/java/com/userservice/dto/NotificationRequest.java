package com.userservice.dto;

import com.userservice.enums.MessageType;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationRequest implements Serializable {
    private String to;
    private String subject;
    private String body;
    private MessageType type;
}
