package com.usermanagement.service;

import com.usermanagement.dto.RoleDTO;

import java.util.List;

public interface IRoleService {

    RoleDTO addRole(RoleDTO roleDTO);

    List<RoleDTO> getAllRoles();

    RoleDTO getRoleByName(String roleName);

    void assignRoleToUser(String username, String roleName);
}
