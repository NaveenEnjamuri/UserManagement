package com.usermanagement.converter;

import com.usermanagement.dto.RoleDTO;
import com.usermanagement.entity.Role;

import java.util.List;
import java.util.stream.Collectors;

public class RoleConverter {

    public static RoleDTO toDTO(Role role) {
        if (role == null) return null;
        return RoleDTO.builder()
                .id(role.getId())
                .name(role.getName())
                .build();
    }

    public static Role toEntity(RoleDTO dto) {
        if (dto == null) return null;
        return Role.builder()
                .id(dto.getId())
                .name(dto.getName())
                .build();
    }

    public static List<RoleDTO> toDTOList(List<Role> roles) {
        return roles == null ? null : roles.stream().map(RoleConverter::toDTO).collect(Collectors.toList());
    }

    public static List<Role> toEntityList(List<RoleDTO> dtos) {
        return dtos == null ? null : dtos.stream().map(RoleConverter::toEntity).collect(Collectors.toList());
    }
}
