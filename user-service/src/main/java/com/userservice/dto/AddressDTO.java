// --- 📁 dto/AddressDTO.java ---
package com.userservice.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDTO {
//    private Long id;              // ✅ Added
    private String street;
    private String city;
    private String state;
    private String pincode;
}
