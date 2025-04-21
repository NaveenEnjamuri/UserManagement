// UserProfileDTO.java
package com.usermanagement.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileDTO {
    private String fullName;
    private String phone;
    private String profilePic;
    private String dateOfBirth;
    private AddressDTO address;
}
