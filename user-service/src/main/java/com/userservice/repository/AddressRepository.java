// AddressRepository.java
package com.usermanagement.repository;

import com.usermanagement.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
