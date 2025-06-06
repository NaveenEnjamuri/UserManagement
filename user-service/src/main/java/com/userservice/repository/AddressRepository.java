// AddressRepository.java
package com.userservice.repository;

import com.userservice.entity.Address;
import com.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<Address> findByUser(User user);
//    Optional<Address> findFirstByUser(User user);
}

