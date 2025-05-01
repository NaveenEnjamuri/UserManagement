package com.userservice.service;

import com.userservice.dto.AddressDTO;
import com.userservice.dto.UpdateProfileDTO;
import com.userservice.dto.UserDTO;

public interface IUserService {
    UserDTO registerUser(UserDTO userDTO);
    UserDTO getUserByUsername(String username);
    UserDTO getUserByEmail(String email);

//    UserDTO updateUserProfile(UserDTO updatedUser, String username);
    UserDTO updateUserProfile(UpdateProfileDTO dto, String username);

    void deleteUser(Long userId);
    AddressDTO getAddressByUsername(String username);
    UserDTO updateUserAddress(AddressDTO addressDTO, String username);
    void forgotPassword(String email);
    void resetPassword(String token, String newPassword);
}





//// --- 📁 service/IUserService.java ---
//package com.userservice.service;
//
//import com.userservice.dto.*;
//public interface IUserService {
//    UserDTO registerUser(UserDTO userDTO);
//    UserDTO getUserByUsername(String username);
//    UserDTO getUserByEmail(String email);
//    UserProfileDTO getUserProfile(String username);
//    UserDTO updateUserProfile(UserProfileDTO profileDTO, String username);
//    void deleteUser(Long userId);
//    AddressDTO getAddressByUsername(String username);
//    UserDTO updateUserAddress(AddressDTO addressDTO, String username);
//    void forgotPassword(String email);
//    void resetPassword(String token, String newPassword);
//}











//package com.userservice.service;
//
//import com.userservice.dto.UserDTO;
//import com.userservice.dto.UserProfileDTO;
//import com.userservice.dto.AddressDTO;
//
//import java.util.Optional;
//
//public interface IUserService {
//
//    UserDTO registerUser(UserDTO userDTO);
//
//    UserDTO getUserByUsername(String username);
//
//    UserDTO getUserByEmail(String email);
//
//    UserProfileDTO getUserProfile(String username);
//
//    UserDTO updateUserProfile(UserProfileDTO profileDTO, String username);
//
//    void deleteUser(Long userId);
//
//    AddressDTO getAddressByUsername(String username);
//
//    UserDTO updateUserAddress(AddressDTO addressDTO, String username);
//
//    void forgotPassword(String email);
//
//    void resetPassword(String token, String newPassword);
//}
