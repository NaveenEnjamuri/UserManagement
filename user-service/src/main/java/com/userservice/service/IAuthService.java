package com.usermanagement.service;

import com.usermanagement.dto.UserDTO;

public interface IAuthService {

    UserDTO login(String username, String password);

    void logout();

    String generateResetPasswordToken(String email);

    void changePassword(String username, String oldPassword, String newPassword);
}
