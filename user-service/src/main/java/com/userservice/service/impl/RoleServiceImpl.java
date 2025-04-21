package com.usermanagement.service.impl;

import com.usermanagement.dto.RoleDTO;
import com.usermanagement.entity.Role;
import com.usermanagement.exception.ResourceNotFoundException;
import com.usermanagement.repository.RoleRepository;
import com.usermanagement.service.IRoleService;
import com.usermanagement.util.RoleConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleServiceImpl implements IRoleService {

    private final RoleRepository roleRepository;
    private final RoleConverter roleConverter;

    @Override
    public RoleDTO createRole(RoleDTO roleDTO) {
        Role role = roleConverter.toEntity(roleDTO);
        Role saved = roleRepository.save(role);
        log.info("✅ Role '{}' created successfully", saved.getName());
        return roleConverter.toDTO(saved);
    }

    @Override
    public List<RoleDTO> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        if (roles.isEmpty()) {
            throw new ResourceNotFoundException("No roles found.");
        }
        log.info("📥 Retrieved {} roles", roles.size());
        return roles.stream()
                .map(roleConverter::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RoleDTO getRoleByName(String roleName) {
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with name: " + roleName));
        log.info("🔍 Role found: {}", roleName);
        return roleConverter.toDTO(role);
    }

    @Override
    public RoleDTO updateRole(Long roleId, RoleDTO roleDTO) {
        Role existing = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with ID: " + roleId));

        existing.setName(roleDTO.getName());
        existing.setDescription(roleDTO.getDescription());

        Role updated = roleRepository.save(existing);
        log.info("🔁 Role '{}' updated successfully", updated.getName());
        return roleConverter.toDTO(updated);
    }

    @Override
    public void deleteRole(Long roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with ID: " + roleId));

        roleRepository.delete(role);
        log.info("🗑️ Role '{}' deleted", role.getName());
    }
}
