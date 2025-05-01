// --- 📁 dto/UserDTO.java ---
package com.userservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

    private Long id;
    private String fullName;
    private String username;
    private String email;
    private String phone;
    private String password;

    private AddressDTO address;
    private List<String> roles;

    @JsonIgnore // ✅ So clients won't send it manually
    private String createdAt; // optional (for response readability)

    @JsonIgnore // ✅ So clients won't send it manually
    private String updatedAt;


//    private Long id;              // ✅ Added this line
//    private String fullName;
//    private String username;
//    private String email;
//    private String phone;
//    private String password;
//
//    private AddressDTO address;
//    private UserProfileDTO profile;
//
//    @ApiModelProperty(example = "[\"ROLE_USER\"]")
//    private List<String> roles;




//    private List<String> roles; // expects list of ROLE_USER, ROLE_ADMIN, etc

//    public UserDTO(String username, String email, Object o) {
//    }
}
