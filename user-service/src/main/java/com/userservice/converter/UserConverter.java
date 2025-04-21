package com.usermanagement.converter;

import com.usermanagement.dto.UserDTO;
import com.usermanagement.entity.User;

public class UserConverter {

    public static UserDTO toDTO(User user) {
        if (user == null) return null;
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .roles(RoleConverter.toDTOList(user.getRoles()))
                .address(AddressConverter.toDTO(user.getAddress()))
                .profile(UserProfileConverter.toDTO(user.getProfile()))
                .build();
    }

    public static User toEntity(UserDTO dto) {
        if (dto == null) return null;
        return User.builder()
                .id(dto.getId())
                .username(dto.getUsername())
                .email(dto.getEmail())
                .phoneNumber(dto.getPhoneNumber())
                .roles(RoleConverter.toEntityList(dto.getRoles()))
                .address(AddressConverter.toEntity(dto.getAddress()))
                .profile(UserProfileConverter.toEntity(dto.getProfile()))
                .build();
    }
}
