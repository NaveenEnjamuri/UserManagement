package com.usermanagement.service;

import com.usermanagement.dto.AddressDTO;

public interface IAddressService {

    AddressDTO addAddress(AddressDTO addressDTO);

    AddressDTO getAddressByUser(String username);

    AddressDTO updateAddress(AddressDTO addressDTO, String username);

    void deleteAddress(String username);
}
