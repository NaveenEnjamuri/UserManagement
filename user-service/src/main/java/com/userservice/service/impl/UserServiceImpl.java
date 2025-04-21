package com.usermanagement.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.usermanagement.config.RedisConfig;
import com.usermanagement.converter.AddressConverter;
import com.usermanagement.converter.UserConverter;
import com.usermanagement.converter.UserProfileConverter;
import com.usermanagement.dto.*;
import com.usermanagement.entity.*;
import com.usermanagement.enums.MessageType;
import com.usermanagement.enums.RoleName;
import com.usermanagement.exception.UserAlreadyExistsException;
import com.usermanagement.exception.UserNotFoundException;
import com.usermanagement.rabbitmq.RabbitMQPublisher;
import com.usermanagement.repository.RoleRepository;
import com.usermanagement.repository.UserRepository;
import com.usermanagement.service.IUserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final UserConverter userConverter;
    private final UserProfileConverter userProfileConverter;
    private final AddressConverter addressConverter;

    private final RabbitMQPublisher rabbitMQPublisher;
    private final RedisTemplate<String, Object> redisTemplate;
    private final PasswordEncoder passwordEncoder;

    private static final String USERNAME_CACHE = "user::username::";
    private static final String EMAIL_CACHE = "user::email::";
    private static final Duration CACHE_TTL = Duration.ofMinutes(10);

    private final ObjectMapper objectMapper = new ObjectMapper();

    // ====================== 1. Register User ============================
    @Override
    public UserDTO registerUser(UserDTO userDTO) {
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new UserAlreadyExistsException("Username already exists");
        }

        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new UserAlreadyExistsException("Email already registered");
        }

        User user = userConverter.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        // Assign default USER role
        Role role = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Default role not found"));
        user.getRoles().add(role);

        userRepository.save(user);

        // Send registration success email
        try {
            NotificationRequest notification = new NotificationRequest(
                    user.getEmail(), "Welcome to User Management!",
                    "Hi " + user.getFullName() + ", your registration was successful.",
                    MessageType.REGISTRATION
            );
            rabbitMQPublisher.publish(notification);
        } catch (Exception e) {
            log.error("Failed to send registration email: {}", e.getMessage());
        }

        return userConverter.toDto(user);
    }

    // ====================== 2. Get User By Username ============================
    @Override
    public UserDTO getUserByUsername(String username) {
        String redisKey = USERNAME_CACHE + username;
        UserDTO cachedUser = (UserDTO) redisTemplate.opsForValue().get(redisKey);

        if (cachedUser != null) {
            return cachedUser;
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));

        UserDTO dto = userConverter.toDto(user);
        redisTemplate.opsForValue().set(redisKey, dto, CACHE_TTL);
        return dto;
    }

    // ====================== 3. Get User By Email ============================
    @Override
    public UserDTO getUserByEmail(String email) {
        String redisKey = EMAIL_CACHE + email;
        UserDTO cachedUser = (UserDTO) redisTemplate.opsForValue().get(redisKey);

        if (cachedUser != null) {
            return cachedUser;
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));

        UserDTO dto = userConverter.toDto(user);
        redisTemplate.opsForValue().set(redisKey, dto, CACHE_TTL);
        return dto;
    }

    // ====================== 4. Get User Profile ============================
    @Override
    public UserProfileDTO getUserProfile(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found for profile view"));

        return userProfileConverter.toDto(user.getUserProfile());
    }

    // Continuing inside UserServiceImpl...

    // ====================== 5. Update User Profile ============================
    @Override
    public UserDTO updateUserProfile(UserProfileDTO profileDTO, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found to update profile"));

        UserProfile profile = user.getUserProfile();
        if (profile == null) {
            profile = new UserProfile();
            user.setUserProfile(profile);
        }

        userProfileConverter.updateEntity(profileDTO, profile);
        User updatedUser = userRepository.save(user);

        // Clear Redis cache
        redisTemplate.delete(USERNAME_CACHE + username);
        redisTemplate.delete(EMAIL_CACHE + updatedUser.getEmail());

        return userConverter.toDto(updatedUser);
    }

    // ====================== 6. Delete User ============================
    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found to delete"));

        userRepository.deleteById(userId);

        // Delete from cache
        redisTemplate.delete(USERNAME_CACHE + user.getUsername());
        redisTemplate.delete(EMAIL_CACHE + user.getEmail());

        // Send deletion alert
        try {
            NotificationRequest notification = new NotificationRequest(
                    user.getEmail(), "Account Deleted",
                    "Dear " + user.getFullName() + ", your account has been deleted.",
                    MessageType.ALERT
            );
            rabbitMQPublisher.publish(notification);
        } catch (Exception e) {
            log.error("Failed to send account deletion email: {}", e.getMessage());
        }
    }

    // ====================== 7. Get Address By Username ============================
    @Override
    public AddressDTO getAddressByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found to fetch address"));

        Address address = user.getAddress();
        if (address == null) {
            throw new UserNotFoundException("No address found for user: " + username);
        }

        return addressConverter.toDto(address);
    }

    // ====================== 8. Update User Address ============================
    @Override
    public UserDTO updateUserAddress(AddressDTO addressDTO, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found to update address"));

        Address address = user.getAddress();
        if (address == null) {
            address = new Address();
            user.setAddress(address);
        }

        addressConverter.updateEntity(addressDTO, address);
        User updatedUser = userRepository.save(user);

        // Clear Redis cache
        redisTemplate.delete(USERNAME_CACHE + username);
        redisTemplate.delete(EMAIL_CACHE + updatedUser.getEmail());

        return userConverter.toDto(updatedUser);
    }


    // ====================== 9. Forgot Password (Send Reset Email) ============================
    @Override
    public void forgotPassword(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found to send reset link"));

        String resetToken = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set("resetToken::" + resetToken, user.getUsername(), Duration.ofMinutes(30));

        // Send reset password link
        try {
            String resetUrl = "http://yourapp.com/reset-password?token=" + resetToken;
            NotificationRequest notification = new NotificationRequest(
                    email, "Reset Your Password",
                    "Click this link to reset your password: " + resetUrl,
                    MessageType.RESET
            );
            rabbitMQPublisher.publish(notification);
        } catch (Exception e) {
            log.error("Failed to send reset password email: {}", e.getMessage());
        }
    }

    // ====================== 10. Reset Password (Token Validation) ============================
    @Override
    public void resetPassword(String token, String newPassword) {
        String redisKey = "resetToken::" + token;
        String username = (String) redisTemplate.opsForValue().get(redisKey);

        if (username == null) {
            throw new RuntimeException("Reset token is invalid or expired");
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found for password reset"));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        redisTemplate.delete(redisKey);

        try {
            NotificationRequest notification = new NotificationRequest(
                    user.getEmail(), "Password Changed",
                    "Your password has been successfully updated.",
                    MessageType.RESET_SUCCESS
            );
            rabbitMQPublisher.publish(notification);
        } catch (Exception e) {
            log.error("Failed to send password reset confirmation email: {}", e.getMessage());
        }
    }
}

//}
