// --- ✅ Integration Tests: UserServiceTest.java ---
package com.userservice;

import com.userservice.dto.*;
import com.userservice.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UserServiceTest {

    @Autowired
    private IUserService userService;

    @Test
    public void testRegisterUser() {
        UserDTO dto = UserDTO.builder()
                .email("testuser@mail.com")
                .fullName("Test User")
                .username("testuser")
                .password("Test@123")
                .phone("9999999999")
                .build();

        UserDTO saved = userService.registerUser(dto);

        assertThat(saved).isNotNull();
        assertThat(saved.getUsername()).isEqualTo("testuser");
        assertThat(saved.getEmail()).isEqualTo("testuser@mail.com");
    }

    @Test
    public void testGetUserByUsername() {
        testRegisterUser();
        UserDTO user = userService.getUserByUsername("testuser");
        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo("testuser");
    }

    @Test
    public void testUpdateUserProfile() {
        testRegisterUser();
//        UserProfileDTO profileDTO = UserProfileDTO.builder()
        UpdateProfileDTO profileDTO = UpdateProfileDTO.builder()
                .email("updated@mail.com")
                .fullName("Updated Name")
                .phone("8888888888")
                .build();

        UserDTO updated = userService.updateUserProfile(profileDTO, "testuser");
        assertThat(updated.getEmail()).isEqualTo("updated@mail.com");
        assertThat(updated.getFullName()).isEqualTo("Updated Name");
    }

    @Test
    public void testDeleteUser() {
        UserDTO dto = UserDTO.builder()
                .email("delete@mail.com")
                .fullName("Delete Me")
                .username("deleteuser")
                .password("Del@123")
                .phone("1111111111")
                .build();

        UserDTO saved = userService.registerUser(dto);
//        userService.deleteUser(saved.getId());
        userService.deleteUser(saved.getUsername());
        // The next line should throw an exception if user not found
        try {
            userService.getUserByUsername("deleteuser");
        } catch (Exception e) {
            assertThat(e).hasMessageContaining("not found");
        }
    }
}








//// --- ✅ Integration Tests: UserServiceTest.java ---
//package com.userservice;
//
//import com.userservice.dto.*;
//import com.userservice.service.IUserService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.DirtiesContext;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
//public class UserServiceTest {
//
//    @Autowired
//    private IUserService userService;
//
//    //	@Test
////	public void testRegisterUser() {
////		UserDTO dto = UserDTO.builder()
////				.email("test@mail.com")
////				.fullName("Test User")
////				.username("testuser")
////				.password("Test@123")
////				.phone("9999999999")
////				.build();
////
////		UserDTO saved = userService.registerUser(dto);
////		assertThat(saved).isNotNull();
////		assertThat(saved.getUsername()).isEqualTo("testuser");
////	}
//
//    @Test
//    public void testRegisterUser() {
//        UserDTO dto = UserDTO.builder()
//                .email("testuser@mail.com")
//                .fullName("Test User")
//                .username("testuser")
//                .password("Test@123")
//                .phone("9999999999")
//                .build();
//
//        UserDTO saved = userService.registerUser(dto);
//
//        assertThat(saved).isNotNull();
//        assertThat(saved.getUsername()).isEqualTo("testuser");
//        assertThat(saved.getEmail()).isEqualTo("testuser@mail.com");
//    }
//
//    @Test
//    public void testGetUserByUsername() {
//        testRegisterUser();
//        UserDTO user = userService.getUserByUsername("testuser");
//        assertThat(user).isNotNull();
//        assertThat(user.getUsername()).isEqualTo("testuser");
//    }
//
//    @Test
//    public void testUpdateUserProfile() {
//        testRegisterUser();
//        UserProfileDTO profileDTO = UserProfileDTO.builder()
//                .email("updated@mail.com")
//                .fullName("Updated Name")
//                .phone("8888888888")
//                .build();
//
//        UserDTO updated = userService.updateUserProfile(profileDTO, "testuser");
//
//        assertThat(updated.getEmail()).isEqualTo("updated@mail.com");
//        assertThat(updated.getFullName()).isEqualTo("Updated Name");
//    }
//
//    @Test
//    public void testDeleteUser() {
//        UserDTO dto = UserDTO.builder()
//                .email("delete@mail.com")
//                .fullName("Delete Me")
//                .username("deleteuser")
//                .password("Del@123")
//                .phone("1111111111")
//                .build();
//
//        UserDTO saved = userService.registerUser(dto);
//        Long userId = saved.getId();
//        userService.deleteUser(userId);
//
//        try {
//            userService.getUserByUsername("deleteuser");
//        } catch (Exception e) {
//            assertThat(e).hasMessageContaining("not found");
//        }
//    }
//}
