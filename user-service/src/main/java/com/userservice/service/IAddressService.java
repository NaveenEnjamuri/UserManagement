// --- 📁 service/IAddressService.java ---
package com.userservice.service;

import com.userservice.dto.AddressDTO;

public interface IAddressService {
    AddressDTO getAddressByUsername(String username);
    AddressDTO updateAddress(String username, AddressDTO addressDTO);
}




//package com.userservice.service;
//
//import com.userservice.dto.AddressDTO;
//
//public interface IAddressService {
//
//    AddressDTO addAddress(AddressDTO addressDTO);
//
//    AddressDTO getAddressByUser(String username);
//
//    AddressDTO updateAddress(AddressDTO addressDTO, String username);
//
//    void deleteAddress(String username);
//}
