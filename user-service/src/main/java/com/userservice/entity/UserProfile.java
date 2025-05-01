// --- 📁 entity/UserProfile.java ---
package com.userservice.entity;
public class UserProfile {}


//import lombok.*;
//import javax.persistence.*;
//import java.time.LocalDateTime;
//
//@Entity
//@Getter @Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class UserProfile {
//    //public class UserProfile extends BaseEntity{
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String fullName;
//    private String email;
//    private String phone;
//
//    @OneToOne
//    @JoinColumn(name = "user_id")
//    private User user;
//
//    private LocalDateTime createdAt;
//    private LocalDateTime updatedAt;
//
//    @PrePersist
//    public void onCreate() {
//        createdAt = updatedAt = LocalDateTime.now();
//    }
//
//    @PreUpdate
//    public void onUpdate() {
//        updatedAt = LocalDateTime.now();
//    }
//}





//// UserProfile.java
//package com.userservice.entity;
//
//import lombok.*;
//
//import javax.persistence.*;
//
//@Entity
//@Table(name = "user_profiles")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class UserProfile {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String fullName;
//    private String phone;
//    private String profilePic;
//    private String dateOfBirth;
//
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "address_id")
//    private Address address;
//
//    @OneToOne
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;
//}
