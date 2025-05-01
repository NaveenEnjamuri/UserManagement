// --- 📁 service/impl/AddressServiceImpl.java ---
package com.userservice.service.impl;

import com.userservice.converter.AddressConverter;
import com.userservice.dto.AddressDTO;
import com.userservice.entity.Address;
import com.userservice.entity.User;
import com.userservice.exception.UserNotFoundException;
import com.userservice.repository.AddressRepository;
import com.userservice.repository.UserRepository;
import com.userservice.service.IAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements IAddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final AddressConverter addressConverter;

    @Override
    public AddressDTO getAddressByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + username));
        Address address = addressRepository.findByUser(user)
                .orElseThrow(() -> new UserNotFoundException("Address not found for user."));
        return addressConverter.toDTO(address);
    }

    @Override
    public AddressDTO updateAddress(String username, AddressDTO addressDTO) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found."));

        Address address = addressRepository.findByUser(user).orElse(new Address());
        address.setUser(user);
        address.setCity(addressDTO.getCity());
        address.setState(addressDTO.getState());
        address.setPincode(addressDTO.getPincode());
        address.setStreet(addressDTO.getStreet());

        Address saved = addressRepository.save(address);
        return addressConverter.toDTO(saved);
    }
}
