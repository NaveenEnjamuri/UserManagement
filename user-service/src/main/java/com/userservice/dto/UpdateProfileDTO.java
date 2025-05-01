package com.userservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateProfileDTO {
    private String fullName;
    private String email;
    private String phone;
}
