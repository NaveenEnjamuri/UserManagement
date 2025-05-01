// RoleRepository.java
package com.userservice.repository;

import com.userservice.entity.Role;
import com.userservice.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
//    Optional<Role> findByName(String name);
    Optional<Role> findByName(RoleName name);

}
