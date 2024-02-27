package org.example.datastoreretrieveservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.example.base.enums.RoleEnum;
import org.example.base.enums.UserStatusEnum;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.UUID;

/**
 * @author Ashish
 * @date 04-Feb-2024
 *
 */

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@Builder
@Document(collection = "UserDetails")
public class UserDetails extends BaseEntity{
    @Id
    @Field("user_id")
    private String userId;

    @Field("user_name")
    private String userName;

    @Field("name")
    private String name;

    @Field("last_name")
    private String lastName;

    @Field("role")
    private String role;
    @Field("mobile_no")
    private String mobileNo;

    @Field("email_id")
    private String emailId;

    @Field("user_status")
    private String userStatusEnum;

    public UserDetails() {
        this.userId = UUID.randomUUID().toString();
    }
}
