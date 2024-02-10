package org.example.pojo.model;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Builder
public class User {
    private String userId;
    private String userName;
    private String name;
    private String lastName;
    private String role;
    private String mobileNo;
    private String emailId;
}
