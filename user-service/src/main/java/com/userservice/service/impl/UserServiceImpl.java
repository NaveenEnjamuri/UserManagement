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
import com.userservice.exception.AddressNotFoundException;
import com.userservice.exception.UserAlreadyExistsException;
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

//    @Override
//    public UserDTO registerUser(UserDTO userDTO) {
//        if (!userDTO.getEmail().matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
//            throw new IllegalArgumentException("Invalid email format");
//        }
//
//        if (userDTO.getPassword().length() < 8) {
//            throw new IllegalArgumentException("Password must be at least 8 characters long");
//        }
//
//        // ✅ Unique email check
//        if (userRepository.existsByEmail(userDTO.getEmail())) {
//            throw new UserAlreadyExistsException("Email is already registered.");
//        }
//
//        // ✅ Unique username check
//        if (userRepository.existsByUsername(userDTO.getUsername())) {
//            throw new UserAlreadyExistsException("Username is already taken.");
//        }
//
//        User user = userConverter.toEntity(userDTO);
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        user.setActive(true); // ensure default active
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


    @Override
    public UserDTO getUserByUsername(String username) {
//        User user = userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found with username: " + username));
        try {
          User user = userRepository.findByUsernameAndActiveTrue(username)
                    .orElseThrow(() -> new UserNotFoundException("Active user not found with username: " + username));

        return userConverter.toDTO(user);
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserDTO getUserByEmail(String email) {
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
        try {
            User user = userRepository.findByEmailAndActiveTrue(email)
                    .orElseThrow(() -> new UserNotFoundException("Active user not found with email: " + email));

        return userConverter.toDTO(user);
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserDTO updateUserProfile(UpdateProfileDTO dto, String username) {
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new UserNotFoundException("User not found"));
        try {
            User user = userRepository.findByUsernameAndActiveTrue(username)
                    .orElseThrow(() -> new UserNotFoundException("Active user not found"));

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
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }
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

//    @Override
//    public void deleteUser(Long userId) {
//        userRepository.deleteById(userId);
//    }

    @Override
    public void deleteUser(String username) {
        try {
         User user = userRepository.findByUsernameAndActiveTrue(username)
                    .orElseThrow(() -> new UserNotFoundException("User not found or already deactivated"));

        user.setActive(false); // Soft delete
        userRepository.save(user);
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public AddressDTO getAddressByUsername(String username) {
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new UserNotFoundException("User not found"));
        User user;
        try {
            user = userRepository.findByUsernameAndActiveTrue(username)
                    .orElseThrow(() -> new UserNotFoundException("Active user not found"));
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }

        Address address;
        try {
            address = addressRepository.findByUser(user)
                    .orElseThrow(() -> new UserNotFoundException("Address not found"));
        } catch (AddressNotFoundException e) {
            throw new RuntimeException(e);
        }

        return addressConverter.toDTO(address);
    }

    @Override
    public UserDTO updateUserAddress(AddressDTO addressDTO, String username) {
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new UserNotFoundException("User not found"));
        try {
            User user = userRepository.findByUsernameAndActiveTrue(username)
                    .orElseThrow(() -> new UserNotFoundException("Active user not found"));

            Address address = addressRepository.findByUser(user).orElse(new Address());
            address.setStreet(addressDTO.getStreet());
            address.setCity(addressDTO.getCity());
            address.setState(addressDTO.getState());
            address.setPincode(addressDTO.getPincode());
            address.setUser(user); // Ensure relationship is preserved

            addressRepository.save(address);
//        Address updated = addressConverter.toEntity(addressDTO);
//        updated.setUser(user);
//        addressRepository.save(updated);

//        return addressConverter.toDTO(address);
            return userConverter.toDTO(user); // need to implement only address dto
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void forgotPassword(String email) {
//        User user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new UserNotFoundException("Email not registered."));
        try {
            User user = userRepository.findByEmailAndActiveTrue(email)
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
        } catch (UserNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void resetPassword(String token, String newPassword) {
        // For demo purpose only: actual logic should validate token and retrieve user
        System.out.println("Token received: " + token);
        // TODO: implement actual logic using token lookup
    }
}