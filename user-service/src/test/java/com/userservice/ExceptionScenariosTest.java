// --- ✅ Integration Test: ExceptionScenariosTest.java ---
package com.userservice;

import com.userservice.service.IAuthService;
import com.userservice.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class ExceptionScenariosTest {

    @Autowired
    private IUserService userService;

    @Autowired
    private IAuthService authService;

    @Test
    public void testLoginFailureWithWrongPassword() {
        assertThatThrownBy(() -> authService.login("invaliduser", "wrongpass"))
                .hasMessageContaining("not found");
    }

    @Test
    public void testGetNonExistentUserThrows() {
        assertThatThrownBy(() -> userService.getUserByUsername("ghost"))
                .hasMessageContaining("User not found");
    }
}
