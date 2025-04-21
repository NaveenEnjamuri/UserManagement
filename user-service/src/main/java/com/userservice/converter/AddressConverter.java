package com.usermanagement.converter;

import com.usermanagement.dto.AddressDTO;
import com.usermanagement.entity.Address;

public class AddressConverter {

    public static AddressDTO toDTO(Address address) {
        if (address == null) return null;
        return AddressDTO.builder()
                .id(address.getId())
                .street(address.getStreet())
                .city(address.getCity())
                .state(address.getState())
                .zipCode(address.getZipCode())
                .build();
    }

    public static Address toEntity(AddressDTO dto) {
        if (dto == null) return null;
        return Address.builder()
                .id(dto.getId())
                .street(dto.getStreet())
                .city(dto.getCity())
                .state(dto.getState())
                .zipCode(dto.getZipCode())
                .build();
    }
}
