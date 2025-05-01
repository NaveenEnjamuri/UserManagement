package com.userservice.service.impl;

import com.userservice.converter.AddressConverter;
import com.userservice.converter.UserConverter;
import com.userservice.dto.AddressDTO;
import com.userservice.dto.NotificationRequest;
import com.userservice.dto.UpdateProfileDTO;
import com.userservice.dto.UserDTO;
import com.userservice.entity.Address;
import com.userservice.entity.User;
import com.userservice.enums.MessageType;
import com.userservice.exception.UserNotFoundException;
import com.userservice.rabbitmq.RabbitMQPublisher;
import com.userservice.repository.AddressRepository;
import com.userservice.repository.UserRepository;
import com.userservice.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

//import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final UserConverter userConverter;
    private final AddressConverter addressConverter;
    private final RabbitMQPublisher rabbitPublisher;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDTO registerUser(UserDTO userDTO) {
        User user = userConverter.toEntity(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepository.save(user);

        rabbitPublisher.sendNotification(new NotificationRequest(
                user.getEmail(),
                "Registration Successful",
                "Welcome, " + user.getFullName(),
                MessageType.REGISTRATION
        ));

        return userConverter.toDTO(user);
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
        return userConverter.toDTO(user);
    }

    @Override
    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
        return userConverter.toDTO(user);
    }

    @Override
    public UserDTO updateUserProfile(UpdateProfileDTO dto, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());

        User saved = userRepository.save(user);

        rabbitPublisher.sendNotification(new NotificationRequest(
                saved.getEmail(),
                "Profile Updated",
                "Hi " + saved.getFullName() + ", your profile was updated.",
                MessageType.PROFILE_UPDATE
        ));

        return userConverter.toDTO(saved);
    }


//    @Override
//    public UserDTO updateUserProfile(UserDTO updatedUser, String username) {
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new UserNotFoundException("User not found"));
//
//        user.setFullName(updatedUser.getFullName());
//        user.setEmail(updatedUser.getEmail());
//        user.setPhone(updatedUser.getPhone());
//
//        User saved = userRepository.save(user);
//
//        rabbitPublisher.sendNotification(new NotificationRequest(
//                saved.getEmail(),
//                "Profile Updated",
//                "Hi " + saved.getFullName() + ", your profile was updated.",
//                MessageType.PROFILE_UPDATE
//        ));
//
//        return userConverter.toDTO(saved);
//    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public AddressDTO getAddressByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Address address = addressRepository.findByUser(user)
                .orElseThrow(() -> new UserNotFoundException("Address not found"));

        return addressConverter.toDTO(address);
    }

    @Override
    public UserDTO updateUserAddress(AddressDTO addressDTO, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Address address = addressRepository.findByUser(user).orElse(new Address());
        Address updated = addressConverter.toEntity(addressDTO);
        updated.setUser(user);
        addressRepository.save(updated);

        return userConverter.toDTO(user);
    }

    @Override
    public void forgotPassword(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Email not registered."));

        String token = UUID.randomUUID().toString();
//        String resetLink = "http://yourdomain.com/reset-password?token=" + token;
        String resetLink = "http://localhost:8081/reset-password?token=" + token;


        rabbitPublisher.sendNotification(new NotificationRequest(
                user.getEmail(),
                "Reset Password",
                "Click here to reset your password: " + resetLink,
                MessageType.PASSWORD_RESET
        ));
    }

    @Override
    public void resetPassword(String token, String newPassword) {
        // For demo purpose only: actual logic should validate token and retrieve user
        System.out.println("Token received: " + token);
        // TODO: implement actual logic using token lookup
    }
}







// --- ✅ service/impl/UserServiceImpl.java ---
//package com.userservice.service.impl;
//
//import com.userservice.converter.AddressConverter;
//import com.userservice.converter.UserConverter;
//import com.userservice.converter.UserProfileConverter;
//import com.userservice.dto.*;
//import com.userservice.entity.Address;
//import com.userservice.entity.User;
//import com.userservice.enums.MessageType;
//import com.userservice.exception.UserNotFoundException;
//import com.userservice.rabbitmq.RabbitMQPublisher;
//import com.userservice.repository.AddressRepository;
//import com.userservice.repository.UserRepository;
//import com.userservice.service.IUserService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.UUID;
//
//@Service
//@RequiredArgsConstructor
//public class UserServiceImpl implements IUserService {
//
//    private final UserRepository userRepository;
//    private final AddressRepository addressRepository;
//    private final UserConverter userConverter;
//    private final UserProfileConverter userProfileConverter;
//    private final AddressConverter addressConverter;
//    private final RabbitMQPublisher rabbitPublisher;
//    private final PasswordEncoder passwordEncoder;
//
//    @Override
//    public UserDTO registerUser(UserDTO userDTO) {
//        User user = userConverter.toEntity(userDTO);
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//
//        if (user.getAddress() != null) user.getAddress().setUser(user);
//        if (user.getUserProfile() != null) user.getUserProfile().setUser(user);
//
//        user = userRepository.save(user);
//
//        rabbitPublisher.sendNotification(new NotificationRequest(
//                user.getEmail(),
//                "Registration Successful",
//                "Welcome, " + user.getFullName(),
//                MessageType.REGISTRATION
//        ));
//
//        return userConverter.toDTO(user);
//    }
//
//    @Override
//    @Cacheable(value = "users", key = "#username")
//    public UserDTO getUserByUsername(String username) {
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
//        return userConverter.toDTO(user);
//    }
//
//    @Override
//    public UserDTO getUserByEmail(String email) {
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
//        return userConverter.toDTO(user);
//    }
//
//    @Override
//    public UserProfileDTO getUserProfile(String username) {
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new UserNotFoundException("Profile not found: " + username));
//        return userProfileConverter.toDTO(user.getUserProfile());
//    }
//
//    @Override
//    @CacheEvict(value = "users", key = "#username")
//    public UserDTO updateUserProfile(UserProfileDTO profileDTO, String username) {
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new UserNotFoundException("User not found for update."));
//
//        user.setFullName(profileDTO.getFullName());
//        user.setPhone(profileDTO.getPhone());
//        user.setEmail(profileDTO.getEmail());
//
//        if (user.getUserProfile() == null) {
//            user.setUserProfile(userProfileConverter.toEntity(profileDTO));
//        } else {
//            user.getUserProfile().setFullName(profileDTO.getFullName());
//            user.getUserProfile().setEmail(profileDTO.getEmail());
//            user.getUserProfile().setPhone(profileDTO.getPhone());
//        }
//
//        user = userRepository.save(user);
//
//        rabbitPublisher.sendNotification(new NotificationRequest(
//                user.getEmail(),
//                "Profile Updated",
//                "Hi, your profile was updated successfully.",
//                MessageType.PROFILE_UPDATE
//        ));
//
//        return userConverter.toDTO(user);
//    }
//
//    @Override
//    @CacheEvict(value = "users", allEntries = true)
//    public void deleteUser(Long userId) {
//        userRepository.deleteById(userId);
//    }
//
//    @Override
//    public AddressDTO getAddressByUsername(String username) {
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new UserNotFoundException("User not found: " + username));
//
//        Address address = addressRepository.findByUser(user)
//                .orElseThrow(() -> new UserNotFoundException("Address not found for user."));
//
//        return addressConverter.toDTO(address);
//    }
//
//    @Override
//    public UserDTO updateUserAddress(AddressDTO addressDTO, String username) {
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new UserNotFoundException("User not found."));
//
//        Address updated = addressConverter.toEntity(addressDTO);
//        updated.setUser(user);
//        addressRepository.save(updated);
//
//        return userConverter.toDTO(user);
//    }
//
//    @Override
//    public void forgotPassword(String email) {
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new UserNotFoundException("Email not registered."));
//
//        String token = UUID.randomUUID().toString();
//        String resetLink = "http://yourdomain.com/reset-password?token=" + token;
//
//        rabbitPublisher.sendNotification(new NotificationRequest(
//                user.getEmail(),
//                "Reset Password",
//                "Click here to reset your password: " + resetLink,
//                MessageType.PASSWORD_RESET
//        ));
//    }
//
//    @Override
//    public void resetPassword(String token, String newPassword) {
//        System.out.println("Reset token used: " + token);
//    }
//} // End of UserServiceImpl
