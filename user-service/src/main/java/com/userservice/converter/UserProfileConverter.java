// --- ✅ Converter: UserProfileConverter.java ---
package com.userservice.converter;

public class UserProfileConverter {}



//import com.userservice.dto.UserProfileDTO;
//import com.userservice.entity.UserProfile;
//import org.springframework.stereotype.Component;
//
//@Component
//public class UserProfileConverter {
//
//    public UserProfile toEntity(UserProfileDTO dto) {
//        return UserProfile.builder()
//                .email(dto.getEmail())
//                .phone(dto.getPhone())
//                .fullName(dto.getFullName())
//                .build();
//    }
//
//    public UserProfileDTO toDTO(UserProfile profile) {
//        return UserProfileDTO.builder()
//                .fullName(profile.getFullName())
//                .email(profile.getEmail())
//                .phone(profile.getPhone())
//                .build();
//    }
//}









//package com.userservice.converter;
//
//import com.userservice.dto.UserProfileDTO;
//import com.userservice.entity.User;
//import org.modelmapper.ModelMapper;
//import org.springframework.stereotype.Component;
////import springfox.documentation.swagger2.mappers.ModelMapper;
//
//@Component
//public class UserProfileConverter {
//
//    private final ModelMapper modelMapper = new ModelMapper();
//
//    public UserProfileDTO toDTO(User user) {
//        return modelMapper.map(user, UserProfileDTO.class);
//    }
//
//    public User toEntity(UserProfileDTO profileDTO) {
//        return modelMapper.map(profileDTO, User.class);
//    }
//
////    public UserProfileDTO toDto(User user) {
////        return UserProfileDTO.builder()
////                .fullName(user.getFullName())
////                .email(user.getEmail())
////                .phone(user.getPhone())
////                .build();
////    }
////
////    public void updateUserFromProfile(User user, UserProfileDTO dto) {
////        user.setFullName(dto.getFullName());
////        user.setEmail(dto.getEmail());
////        user.setPhone(dto.getPhone());
////    }
//}





















//package com.userservice.converter;
//
//import com.userservice.dto.UserProfileDTO;
//import com.userservice.entity.UserProfile;
//
//public class UserProfileConverter {
//
//    public static UserProfileDTO toDTO(UserProfile profile) {
//        if (profile == null) return null;
//        return UserProfileDTO.builder()
//                .id(profile.getId())
//                .firstName(profile.getFirstName())
//                .lastName(profile.getLastName())
//                .dob(profile.getDob())
//                .gender(profile.getGender())
//                .build();
//    }
//
//    public static UserProfile toEntity(UserProfileDTO dto) {
//        if (dto == null) return null;
//        return UserProfile.builder()
//                .id(dto.getId())
//                .firstName(dto.getFirstName())
//                .lastName(dto.getLastName())
//                .dob(dto.getDob())
//                .gender(dto.getGender())
//                .build();
//    }
//}
