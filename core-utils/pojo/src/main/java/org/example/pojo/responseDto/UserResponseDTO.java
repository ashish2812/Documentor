package org.example.pojo.responseDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


/**
 * @author Ashish
 *
 * @date 26-Feb-2024
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDTO {

    @JsonProperty("username")
    private String userName;

    @JsonProperty("name")
    private String name;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("mobileNo")
    private String mobileNo;

    @JsonProperty("emailId")
    private String emailId;

    @JsonProperty("modifiedAt")
    private LocalDateTime modifiedAt;

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @JsonProperty("role")
    private String role;

    @JsonProperty("userStatus")
    private String userStatusEnum;
}
