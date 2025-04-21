package com.usermanagement.controller;

import com.usermanagement.dto.UserDTO;
import com.usermanagement.payload.LoginRequest;
import com.usermanagement.payload.PasswordChangeRequest;
import com.usermanagement.service.IAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Api(value = "Authentication Controller", tags = "Auth Operations")
public class AuthController {

    private final IAuthService authService;

    @ApiOperation("Login user by username and password")
    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request.getUsername(), request.getPassword()));
    }

    @ApiOperation("Logout currently authenticated user")
    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        authService.logout();
        return ResponseEntity.ok("User logged out successfully.");
    }

    @ApiOperation("Change user password with old password")
    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@Valid @RequestBody PasswordChangeRequest request) {
        authService.changePassword(
                request.getUsername(),
                request.getOldPassword(),
                request.getNewPassword()
        );
        return ResponseEntity.ok("Password changed successfully.");
    }

    @ApiOperation("Generate reset password token using email")
    @PostMapping("/generate-reset-token")
    public ResponseEntity<String> generateResetToken(@RequestParam String email) {
        String token = authService.generateResetPasswordToken(email);
        return ResponseEntity.ok("Reset password token generated and sent to email.");
    }
}
