// UserProfileRepository.java
package com.usermanagement.repository;

import com.usermanagement.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    Optional<UserProfile> findByUserUsername(String username);
}
