// --- ✅ Integration Test: PasswordResetTest.java ---
package com.userservice;

import com.userservice.dto.UserDTO;
import com.userservice.service.IAuthService;
import com.userservice.service.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PasswordResetTest {

    @Autowired
    private IUserService userService;

    @Autowired
    private IAuthService authService;

    private final String username = "forgotuser";
    private final String email = "forgot@mail.com";

    @BeforeEach
    public void setup() {
        userService.registerUser(UserDTO.builder()
                .username(username)
                .email(email)
                .password("OldPass123")
                .fullName("Forgot Tester")
                .phone("1234567890")
                .build());
    }

    @Test
    public void testForgotPasswordGeneratesToken() {
        String token = authService.generateResetPasswordToken(email);
        assertThat(token).contains("token-");
    }

    @Test
    public void testChangePasswordSuccess() {
        authService.changePassword(username, "OldPass123", "NewSecure123");
    }
}
