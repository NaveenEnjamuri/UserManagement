package com.userservice.service;

import com.userservice.dto.LoginResponseDTO;

public interface IAuthService {
    LoginResponseDTO login(String username, String password);
    void logout();
    String generateResetPasswordToken(String email);
    void changePassword(String username, String oldPassword, String newPassword);
}




//// --- 📁 service/IAuthService.java ---
//package com.userservice.service;
//
//import com.userservice.dto.UserDTO;
//
//public interface IAuthService {
//    UserDTO login(String username, String password);
//    void logout();
//    String generateResetPasswordToken(String email);
//    void changePassword(String username, String oldPassword, String newPassword);
//}















//package com.userservice.service;
//
//import com.userservice.dto.UserDTO;
//
//public interface IAuthService {
//
//    UserDTO login(String username, String password);
//
//    void logout();
//
//    String generateResetPasswordToken(String email);
//
//    void changePassword(String username, String oldPassword, String newPassword);
//}
