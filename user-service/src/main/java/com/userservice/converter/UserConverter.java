package com.userservice.converter;

import com.userservice.dto.AddressDTO;
import com.userservice.dto.UserDTO;
import com.userservice.entity.Address;
import com.userservice.entity.Role;
import com.userservice.entity.User;
import com.userservice.enums.RoleName;
import com.userservice.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserConverter {

    private final RoleRepository roleRepository;
    private final AddressConverter addressConverter;

    public User toEntity(UserDTO dto) {
        if (dto == null) return null;

        User user = User.builder()
//                .id(dto.getId())
                .fullName(dto.getFullName())
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .phone(dto.getPhone())
                .build();

        if (dto.getAddress() != null) {
            Address address = addressConverter.toEntity(dto.getAddress());
            address.setUser(user);
            user.setAddress(address);
        }

        if (dto.getRoles() != null) {
            Set<Role> roleSet = dto.getRoles().stream()
                    .map(roleStr -> roleRepository.findByName(RoleName.valueOf(roleStr))
                            .orElseThrow(() -> new RuntimeException("Role not found: " + roleStr)))
                    .collect(Collectors.toSet());
            user.setRoles(roleSet);
        }

        return user;
    }

    public UserDTO toDTO(User user) {
        if (user == null) return null;

        return UserDTO.builder()
//                .id(user.getId())
                .fullName(user.getFullName())
                .username(user.getUsername())
                .email(user.getEmail())
                .phone(user.getPhone())
                .address(user.getAddress() != null ? addressConverter.toDTO(user.getAddress()) : null)
                .roles(user.getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toList()))
                .createdAt(user.getCreatedAt() != null ? user.getCreatedAt().toString() : null)
                .updatedAt(user.getUpdatedAt() != null ? user.getUpdatedAt().toString() : null)
                .build();
    }
}
