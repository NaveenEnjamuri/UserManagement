package com.userservice.converter;

import com.userservice.dto.RoleDTO;
import com.userservice.entity.Role;
import com.userservice.enums.RoleName;
import org.springframework.stereotype.Component;

@Component
public class RoleConverter {

    public RoleDTO toDTO(Role role) {
        if (role == null) return null;

        return RoleDTO.builder()
                .id(role.getId())
                .name(role.getName().name()) // Enum to string
                .build();
    }

    public Role toEntity(RoleDTO dto) {
        if (dto == null) return null;

        return Role.builder()
                .id(dto.getId())
                .name(RoleName.valueOf(dto.getName())) // String to Enum
                .build();
    }
}
