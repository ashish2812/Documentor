package org.example.userservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.base.enums.RoleEnum;
import org.example.base.enums.UserStatusEnum;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@Builder
@Entity
@Table(name = "User",
        indexes = {
                @Index(name = "idx_username", columnList = "username"),
                @Index(name = "idx_email", columnList = "emailId")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_email", columnNames = "email"),
                @UniqueConstraint(name = "uk_username", columnNames = "username")})
@NoArgsConstructor
public class UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(name = "username", unique = true)
    private String userName;
    @Column(name = "name")
    private String name;
    @Column(name = "lastName")
    private String lastName;
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private RoleEnum role;
    @Column(name = "mobileNo")
    private String mobileNo;
    @Column(name = "emailId", unique = true)
    private String emailId;
    @Column(name = "password")
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "userstatus")
    private UserStatusEnum userStatusEnum;
    @Column(name = "createdAt")
    private LocalDateTime createdAt;
    @Column(name = "modifiedAt")
    private LocalDateTime modifiedAt;
}
