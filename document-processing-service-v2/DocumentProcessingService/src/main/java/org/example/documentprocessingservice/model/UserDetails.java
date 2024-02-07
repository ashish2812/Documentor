//package org.example.documentprocessingservice.model;
//
//import lombok.*;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.mapping.Document;
//import org.springframework.data.mongodb.core.mapping.Field;
//
//import java.util.Date;
//import java.util.UUID;
//
//@EqualsAndHashCode(callSuper = true)
//@Data
//@AllArgsConstructor
//@Builder
//@Document(collection = "UserDetails")
//public class UserDetails extends BaseEntity{
//
//    @Id
//    @Field("user_id")
//    private String userId;
//
//    @Field("user_name")
//    private String userName;
//
//    @Field("name")
//    private String name;
//
//    @Field("last_name")
//    private String lastName;
//    private String role;
//    @Field("mobile_no")
//    private String mobileNo;
//
//    @Field("email_id")
//    private String emailId;
//
//    public UserDetails() {
//        this.userId = UUID.randomUUID().toString();
//    }
//}
