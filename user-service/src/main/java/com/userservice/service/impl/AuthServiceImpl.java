package com.userservice.service.impl;

import com.userservice.dto.LoginResponseDTO;
import com.userservice.entity.User;
import com.userservice.exception.UserNotFoundException;
import com.userservice.repository.UserRepository;
import com.userservice.service.IAuthService;
import com.userservice.service.JwtUserDetailsService;
import com.userservice.security.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {

    private final UserRepository userRepository;
    private final JwtUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public LoginResponseDTO login(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails;
        try {
            userDetails = userDetailsService.loadUserByUsername(username);
        } catch (UsernameNotFoundException e) {
            throw new RuntimeException(e);
        }
        String token = jwtTokenUtil.generateToken(userDetails);
        String role = userDetails.getAuthorities().iterator().next().getAuthority();

        return new LoginResponseDTO(token, username, role);
    }

    @Override
    public void logout() {
        try {
            SecurityContextHolder.clearContext();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String generateResetPasswordToken(String email) {
        User user;
        try {
//            user = userRepository.findByEmail(email)
//                    .orElseThrow(() -> new UserNotFoundException("Email not registered."));
            user = userRepository.findByEmailAndActiveTrue(email)
                    .orElseThrow(() -> new UserNotFoundException("Email not registered."));
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }
        return "token-" + user.getId();
    }

    @Override
    public void changePassword(String username, String oldPassword, String newPassword) {
        User user;
        try {
//            user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            user = userRepository.findByUsernameAndActiveTrue(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        } catch (UsernameNotFoundException e) {
            throw new RuntimeException(e);
        }

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new IllegalArgumentException("Incorrect old password");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}
