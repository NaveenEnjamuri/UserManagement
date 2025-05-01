package com.userservice.converter;

import com.userservice.dto.AddressDTO;
import com.userservice.entity.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressConverter {

    public AddressDTO toDTO(Address address) {
        if (address == null) return null;

        return AddressDTO.builder()
                .id(address.getId())
                .street(address.getStreet())
                .city(address.getCity())
                .state(address.getState())
                .pincode(address.getPincode())
                .build();
    }

    public Address toEntity(AddressDTO dto) {
        if (dto == null) return null;

        return Address.builder()
                .id(dto.getId())
                .street(dto.getStreet())
                .city(dto.getCity())
                .state(dto.getState())
                .pincode(dto.getPincode())
                .build();
    }
}