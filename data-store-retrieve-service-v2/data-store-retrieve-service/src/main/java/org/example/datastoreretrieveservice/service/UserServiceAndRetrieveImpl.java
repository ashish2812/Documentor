package org.example.datastoreretrieveservice.service;

import org.example.base.enums.RoleEnum;
import org.example.base.enums.UserStatusEnum;
import org.example.datastoreretrieveservice.UserServiceAndRetrive;
import org.example.datastoreretrieveservice.model.UserDetails;
import org.example.pojo.dto.UserRequestDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
public class UserServiceAndRetrieveImpl implements UserServiceAndRetrive {

    @Override
    public UserDetails createUser(UserRequestDTO userRequestDTO) {
        UserDetails userDetails = new UserDetails();
        userDetails.setUserName(userRequestDTO.getUserName());
        userDetails.setName(userRequestDTO.getName());
        userDetails.setLastName(userRequestDTO.getLastName());
        userDetails.setRole(RoleEnum.valueOf(userRequestDTO.getRole()).getName());
        userDetails.setMobileNo(userRequestDTO.getMobileNo());
        userDetails.setEmailId(userRequestDTO.getEmailId());
        userDetails.setUserStatusEnum(Objects.isNull(userRequestDTO.getUserStatusEnum())
                ?UserStatusEnum.ACTIVE.getName()
                : UserStatusEnum.valueOf(userRequestDTO.getName()).getName());
        userDetails.setCreatedAt(LocalDateTime.now());
        userDetails.setModifiedAt(null);
        return userDetails;
    }
}
