// UserDTO.java
package com.usermanagement.dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private String username;
    private String email;
    private String password;
    private boolean enabled;
    private Set<String> roles;
    private UserProfileDTO userProfile;
}
