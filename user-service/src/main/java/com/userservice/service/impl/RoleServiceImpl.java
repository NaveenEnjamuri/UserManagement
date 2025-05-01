// --- 📁 service/impl/RoleServiceImpl.java ---
package com.userservice.service.impl;

import com.userservice.dto.RoleDTO;
import com.userservice.repository.RoleRepository;
import com.userservice.service.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements IRoleService {

    private final RoleRepository roleRepository;

    @Override
    public List<RoleDTO> getAllRoles() {
        return roleRepository.findAll()
                .stream()
                .map(role -> new RoleDTO(role.getId(), role.getName().name()))
                .collect(Collectors.toList());
    }

//    @Override
//    public List<RoleDTO> getAllRoles() {
//        return roleRepository.findAll()
//                .stream()
//                .map(role -> RoleDTO.builder()
//                        .id(role.getId())
//                        .name(role.getName().name())
//                        .build())
//                .collect(Collectors.toList());
//    }
}