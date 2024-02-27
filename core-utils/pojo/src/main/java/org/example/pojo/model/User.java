package org.example.pojo.model;


import lombok.*;

import java.time.LocalDateTime;

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
    private String mobileNo;
    private String emailId;
    private LocalDateTime modifiedAt;
    private LocalDateTime createdAt;
    private String role;
    private String userStatusEnum;
}
