// --- ✅ Integration Test: AuthServiceTest.java ---
package com.userservice;

import com.userservice.dto.UserDTO;
import com.userservice.service.IAuthService;
import com.userservice.service.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AuthServiceTest {

    @Autowired
    private IAuthService authService;

    @Autowired
    private IUserService userService;

    @BeforeEach
    public void setup() {
        // Register test user
        UserDTO dto = UserDTO.builder()
                .email("auth@mail.com")
                .fullName("Auth Tester")
                .username("authuser")
                .password("Auth@123")
                .phone("8888888888")
                .build();
        userService.registerUser(dto);
    }

    @Test
    public void testLoginSuccess() {
        UserDTO user = authService.login("authuser", "Auth@123");
        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo("authuser");
    }

    @Test
    public void testLoginFailure() {
        Exception exception = assertThrows(Exception.class, () ->
                authService.login("authuser", "WrongPassword"));
        assertThat(exception.getMessage()).contains("Bad credentials");
    }

    @Test
    public void testChangePassword() {
        authService.changePassword("authuser", "Auth@123", "NewPass@123");

        // Validate login with new password
        UserDTO user = authService.login("authuser", "NewPass@123");
        assertThat(user.getUsername()).isEqualTo("authuser");
    }

    @Test
    public void testLogout() {
        authService.logout();
        // No assertion needed - just ensures method does not crash
    }
}
