package org.example.pojo.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class User {
    private String userId;
    private String userName;
    private String name;
    private String lastName;
    private String role;
    private String mobileNo;
    private String emailId;
}
