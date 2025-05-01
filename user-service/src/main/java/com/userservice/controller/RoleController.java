// --- ✅ Controller: RoleController.java (Updated) ---
package com.userservice.controller;

import com.userservice.dto.RoleDTO;
import com.userservice.service.IRoleService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
@Api(tags = "Role Controller", description = "Operations related to roles")
public class RoleController {

    private final IRoleService roleService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @ApiOperation("Get all roles")
    public ResponseEntity<List<RoleDTO>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }//    @PreAuthorize("hasRole('ROLE_ADMIN')")

}
