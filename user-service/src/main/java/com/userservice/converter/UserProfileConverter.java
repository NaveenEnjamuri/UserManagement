package com.usermanagement.converter;

import com.usermanagement.dto.UserProfileDTO;
import com.usermanagement.entity.UserProfile;

public class UserProfileConverter {

    public static UserProfileDTO toDTO(UserProfile profile) {
        if (profile == null) return null;
        return UserProfileDTO.builder()
                .id(profile.getId())
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .dob(profile.getDob())
                .gender(profile.getGender())
                .build();
    }

    public static UserProfile toEntity(UserProfileDTO dto) {
        if (dto == null) return null;
        return UserProfile.builder()
                .id(dto.getId())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .dob(dto.getDob())
                .gender(dto.getGender())
                .build();
    }
}
