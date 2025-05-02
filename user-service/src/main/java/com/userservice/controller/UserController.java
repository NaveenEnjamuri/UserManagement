package com.userservice.controller;

import com.userservice.dto.*;
import com.userservice.service.IUserService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Api(tags = "User Controller")
public class UserController {

    private final IUserService userService;

    @PostMapping("/register")
    @ApiOperation("Register a new user")
    public ResponseEntity<UserDTO> register(@Valid @RequestBody UserDTO dto) {
        return ResponseEntity.ok(userService.registerUser(dto));
    }

    @GetMapping("/{username}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @ApiOperation("Get user by username")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    @GetMapping("/email/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation("Get user by email")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @PutMapping("/profile/{username}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @ApiOperation("Update user profile")
    public ResponseEntity<UserDTO> updateProfile(@PathVariable String username,
                                                 @RequestBody UpdateProfileDTO dto) {
//    public ResponseEntity<UserDTO> updateProfile(@PathVariable String username, @RequestBody UserDTO dto) {
        return ResponseEntity.ok(userService.updateUserProfile(dto, username));
    }

    @DeleteMapping("/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation("Soft delete user by username")
    public ResponseEntity<String> deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return ResponseEntity.ok("User soft-deleted");
    }
//    @DeleteMapping("/{userId}")
//    @PreAuthorize("hasRole('ADMIN')")
//    @ApiOperation("Delete user by ID")
//    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
////        userService.deleteUser(userId);
//        return ResponseEntity.ok("User deleted");
//    }

    @GetMapping("/address/{username}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @ApiOperation("Get user address")
    public ResponseEntity<AddressDTO> getAddress(@PathVariable String username) {
        return ResponseEntity.ok(userService.getAddressByUsername(username));
    }

    @PutMapping("/address/{username}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @ApiOperation("Update address")
    public ResponseEntity<UserDTO> updateAddress(@PathVariable String username,
                                                 @RequestBody AddressDTO dto) {
        return ResponseEntity.ok(userService.updateUserAddress(dto, username));
    }

    @PostMapping("/forgot-password")
    @ApiOperation("Send forgot password email")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        userService.forgotPassword(email);
        return ResponseEntity.ok("Reset link sent");
    }

    @PostMapping("/reset-password")
    @ApiOperation("Reset password")
    public ResponseEntity<String> resetPassword(@RequestParam String token,
                                                @RequestParam String newPassword) {
        userService.resetPassword(token, newPassword);
        return ResponseEntity.ok("Password updated");
    }
}
