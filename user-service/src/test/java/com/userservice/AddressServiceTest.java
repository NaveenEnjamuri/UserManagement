// --- ✅ Integration Test: AddressServiceTest.java ---
package com.userservice;

import com.userservice.dto.AddressDTO;
import com.userservice.dto.UserDTO;
import com.userservice.service.IAddressService;
import com.userservice.service.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AddressServiceTest {

    @Autowired
    private IUserService userService;

    @Autowired
    private IAddressService addressService;

    private final String USERNAME = "addressuser";

    @BeforeEach
    public void init() {
        UserDTO dto = UserDTO.builder()
                .email("address@mail.com")
                .fullName("Address Test")
                .username(USERNAME)
                .password("Address@123")
                .phone("7777777777")
                .build();
        userService.registerUser(dto);
    }

    @Test
    public void testUpdateAddress() {
        AddressDTO newAddress = AddressDTO.builder()
                .street("1st Street")
                .city("New York")
                .state("NY")
                .pincode("123456")
                .build();

        AddressDTO saved = addressService.updateAddress(USERNAME, newAddress);
        assertThat(saved).isNotNull();
        assertThat(saved.getCity()).isEqualTo("New York");
    }

    @Test
    public void testGetAddressByUsername() {
        testUpdateAddress(); // Ensure address is first set

        AddressDTO fetched = addressService.getAddressByUsername(USERNAME);
        assertThat(fetched).isNotNull();
        assertThat(fetched.getCity()).isEqualTo("New York");
    }

    @Test
    public void testAddressNotFoundThrows() {
        Exception ex = assertThrows(RuntimeException.class, () ->
                addressService.getAddressByUsername("nonexistent"));
        assertThat(ex.getMessage()).contains("User not found");
    }
}
