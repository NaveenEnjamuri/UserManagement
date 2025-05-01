// --- ✅ Integration Test: RoleServiceTest.java ---
package com.userservice;

import com.userservice.dto.RoleDTO;
import com.userservice.entity.Role;
import com.userservice.enums.RoleName;
import com.userservice.repository.RoleRepository;
import com.userservice.service.IRoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class RoleServiceTest {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private IRoleService roleService;

    @BeforeEach
    public void seedRoles() {
        roleRepository.save(Role.builder().name(RoleName.ROLE_USER).build());
        roleRepository.save(Role.builder().name(RoleName.ROLE_ADMIN).build());
    }

    @Test
    public void testGetAllRoles() {
        List<RoleDTO> roles = roleService.getAllRoles();

        assertThat(roles).isNotNull();
        assertThat(roles.size()).isGreaterThanOrEqualTo(2);
        assertThat(roles.stream().anyMatch(r -> r.getName().equals("ROLE_USER"))).isTrue();
        assertThat(roles.stream().anyMatch(r -> r.getName().equals("ROLE_ADMIN"))).isTrue();
    }
}
