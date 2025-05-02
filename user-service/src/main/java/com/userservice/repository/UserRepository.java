package com.userservice.repository;

import com.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
    boolean existsByUsername(String username);


    Optional<User> findByUsernameAndActiveTrue(String username);

    List<User> findAllByActiveTrue(); // optional, if needed elsewhere

    Optional<User> findByEmailAndActiveTrue(String email);

    boolean existsByUsernameAndActiveTrue(String username);

    boolean existsByEmailAndActiveTrue(String email);

}

