package com.usermanagement.controller;

import com.usermanagement.dto.*;
import com.usermanagement.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Api(value = "User Controller", tags = "User Operations")
public class UserController {

    private final IUserService userService;

    @ApiOperation("Register a new user")
    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.registerUser(userDTO));
    }

    @ApiOperation("Get user by username")
    @GetMapping("/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    @ApiOperation("Get user by email")
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    @ApiOperation("Get user profile by username")
    @GetMapping("/profile/{username}")
    public ResponseEntity<UserProfileDTO> getUserProfile(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserProfile(username));
    }

    @ApiOperation("Update user profile")
    @PutMapping("/profile/{username}")
    public ResponseEntity<UserDTO> updateUserProfile(@PathVariable String username, @Valid @RequestBody UserProfileDTO profileDTO) {
        return ResponseEntity.ok(userService.updateUserProfile(profileDTO, username));
    }

    @ApiOperation("Delete user by ID")
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("User deleted successfully.");
    }

    @ApiOperation("Get user address by username")
    @GetMapping("/address/{username}")
    public ResponseEntity<AddressDTO> getAddressByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.getAddressByUsername(username));
    }

    @ApiOperation("Update user address")
    @PutMapping("/address/{username}")
    public ResponseEntity<UserDTO> updateUserAddress(@PathVariable String username, @Valid @RequestBody AddressDTO addressDTO) {
        return ResponseEntity.ok(userService.updateUserAddress(addressDTO, username));
    }

    @ApiOperation("Initiate forgot password via email")
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        userService.forgotPassword(email);
        return ResponseEntity.ok("Reset password instructions sent to email.");
    }

    @ApiOperation("Reset password using token")
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        userService.resetPassword(token, newPassword);
        return ResponseEntity.ok("Password reset successful.");
    }
}



//
//
//package com.usermanagement.controller;
//
//import com.usermanagement.dto.AddressDTO;
//import com.usermanagement.dto.UserDTO;
//import com.usermanagement.dto.UserProfileDTO;
//import com.usermanagement.service.IUserService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//
//@RestController
//@RequestMapping("/api/users")
//@RequiredArgsConstructor
//@Api(value = "User Controller", tags = "User Operations")
//public class UserController {
//
//    private final IUserService userService;
//
//    @ApiOperation("Register a new user")
//    @PostMapping("/register")
//    public ResponseEntity<UserDTO> registerUser(@Valid @RequestBody UserDTO userDTO) {
//        return ResponseEntity.ok(userService.registerUser(userDTO));
//    }
//
//    @ApiOperation("Get user by username")
//    @GetMapping("/{username}")
//    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
//        return ResponseEntity.ok(userService.getUserByUsername(username));
//    }
//
//    @ApiOperation("Get user by email")
//    @GetMapping("/email/{email}")
//    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
//        return ResponseEntity.ok(userService.getUserByEmail(email));
//    }
//
//    @ApiOperation("Get user profile")
//    @GetMapping("/{username}/profile")
//    public ResponseEntity<UserProfileDTO> getUserProfile(@PathVariable String username) {
//        return ResponseEntity.ok(userService.getUserProfile(username));
//    }
//
//    @ApiOperation("Update user profile")
//    @PutMapping("/{username}/profile")
//    public ResponseEntity<UserDTO> updateUserProfile(
//            @PathVariable String username,
//            @Valid @RequestBody UserProfileDTO profileDTO
//    ) {
//        return ResponseEntity.ok(userService.updateUserProfile(profileDTO, username));
//    }
//
//    @ApiOperation("Delete user by ID")
//    @DeleteMapping("/{userId}")
//    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
//        userService.deleteUser(userId);
//        return ResponseEntity.noContent().build();
//    }
//
//    @ApiOperation("Get user address")
//    @GetMapping("/{username}/address")
//    public ResponseEntity<AddressDTO> getUserAddress(@PathVariable String username) {
//        return ResponseEntity.ok(userService.getAddressByUsername(username));
//    }
//
//    @ApiOperation("Update user address")
//    @PutMapping("/{username}/address")
//    public ResponseEntity<UserDTO> updateUserAddress(
//            @PathVariable String username,
//            @Valid @RequestBody AddressDTO addressDTO
//    ) {
//        return ResponseEntity.ok(userService.updateUserAddress(addressDTO, username));
//    }
//
//    @ApiOperation("Forgot password - send reset token via email")
//    @PostMapping("/forgot-password")
//    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
//        userService.forgotPassword(email);
//        return ResponseEntity.ok("Reset password instructions sent to email.");
//    }
//
//    @ApiOperation("Reset password using token")
//    @PostMapping("/reset-password")
//    public ResponseEntity<String> resetPassword(
//            @RequestParam String token,
//            @RequestParam String newPassword
//    ) {
//        userService.resetPassword(token, newPassword);
//        return ResponseEntity.ok("Password reset successfully.");
//    }
//}
