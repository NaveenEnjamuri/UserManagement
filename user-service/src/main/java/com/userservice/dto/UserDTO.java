package com.userservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description = "Data Transfer Object for User")
public class UserDTO {

    @ApiModelProperty(hidden = true) // hides in Swagger
    private Long id;

    @ApiModelProperty(value = "Full name of the user", example = "John Doe", required = true)
    @NotBlank(message = "Full name is required")
    private String fullName;

    @ApiModelProperty(value = "Unique username", example = "john_doe", required = true)
    @NotBlank(message = "Username is required")
    private String username;

    @ApiModelProperty(value = "Email address", example = "john@example.com", required = true)
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @ApiModelProperty(value = "Phone number", example = "9876543210", required = true)
    @NotBlank(message = "Phone number is required")
    private String phone;

    @ApiModelProperty(value = "Password (min 8 characters)", required = true)
    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @ApiModelProperty(value = "User's address")
    private AddressDTO address;

    @ApiModelProperty(value = "List of roles", example = "[\"ROLE_USER\"]", required = true)
    @NotEmpty(message = "At least one role must be provided")
    private List<String> roles; // expects list of ROLE_USER, ROLE_ADMIN, etc

    @JsonIgnore // ✅ So clients won't send it manually
    private String createdAt; // optional (for response readability)

    @JsonIgnore // ✅ So clients won't send it manually
    private String updatedAt;

}
