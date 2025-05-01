// --- 📁 service/IRoleService.java ---
package com.userservice.service;

import com.userservice.dto.RoleDTO;

import java.util.List;

public interface IRoleService {
    List<RoleDTO> getAllRoles();
}





//package com.userservice.service;
//
//import com.userservice.dto.RoleDTO;
//
//import java.util.List;
//
//public interface IRoleService {
//
////    RoleDTO addRole(RoleDTO roleDTO);
//
//    List<RoleDTO> getAllRoles();
//
////    RoleDTO getRoleByName(String roleName);
//
////    void assignRoleToUser(String username, String roleName);
//
//    RoleDTO getRoleById(Long id);
//
//    RoleDTO createRole(RoleDTO roleDTO);
//
//    RoleDTO updateRole(Long id, RoleDTO roleDTO);
//
//    void deleteRole(Long id);
//
//}
