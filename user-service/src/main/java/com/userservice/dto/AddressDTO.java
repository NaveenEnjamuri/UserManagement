// AddressDTO.java
package com.usermanagement.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDTO {
    private String line1;
    private String line2;
    private String city;
    private String state;
    private String zipCode;
    private String country;
}
