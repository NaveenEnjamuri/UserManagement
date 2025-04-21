package com.usermanagement.service;

import com.usermanagement.dto.UserDTO;
import com.usermanagement.dto.UserProfileDTO;
import com.usermanagement.dto.AddressDTO;

import java.util.Optional;

public interface IUserService {

    UserDTO registerUser(UserDTO userDTO);

    UserDTO getUserByUsername(String username);

    UserDTO getUserByEmail(String email);

    UserProfileDTO getUserProfile(String username);

    UserDTO updateUserProfile(UserProfileDTO profileDTO, String username);

    void deleteUser(Long userId);

    AddressDTO getAddressByUsername(String username);

    UserDTO updateUserAddress(AddressDTO addressDTO, String username);

    void forgotPassword(String email);

    void resetPassword(String token, String newPassword);
}
