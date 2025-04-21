package com.usermanagement.service.impl;

import com.usermanagement.dto.UserDTO;
import com.usermanagement.entity.User;
import com.usermanagement.exception.ApiException;
import com.usermanagement.rabbitmq.RabbitMQPublisher;
import com.usermanagement.repository.UserRepository;
import com.usermanagement.service.IAuthService;
import com.usermanagement.util.ModelMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthServiceImpl implements IAuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private RabbitMQPublisher rabbitMQPublisher;

    @Override
    public UserDTO login(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ApiException("User not found for login"));

        return ModelMapperUtils.map(user, UserDTO.class);
    }

    @Override
    public void logout() {
        SecurityContextHolder.clearContext();
        // Additional logic for token/session invalidation can be added here if using JWT

        // Clear session/token from Redis (if session-based or token-based approach is used)
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getName() != null) {
            String key = "SESSION::" + auth.getName();
            redisTemplate.delete(key);
        }
    }

    @Override
    public String generateResetPasswordToken(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiError("User not found with email: " + email));

        String token = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set("RESET_PASSWORD_TOKEN::" + token, email, 15, TimeUnit.MINUTES);

        // Notify via email using RabbitMQ   // Publish to RabbitMQ to send email
        rabbitMQPublisher.sendResetPasswordEmail(user.getEmail(), token);

        return token;

    }

    @Override
    public void changePassword(String username, String oldPassword, String newPassword) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ApiError("User not found with username: " + username));

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new ApiError("Old password does not match");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        // Notify via email using RabbitMQ      // Send confirmation email
        rabbitMQPublisher.sendChangePasswordEmail(user.getEmail());

    }
} 
