package org.example.userservice.serviceImp;


import lombok.extern.slf4j.Slf4j;
import org.example.base.enums.RoleEnum;
import org.example.base.enums.UserStatusEnum;
import org.example.base.exceptions.DocumentorException;
import org.example.base.exceptions.DocumentorValidationException;
import org.example.base.utils.CommonUtils;
import org.example.base.utils.CommonValidations;
import org.example.kafka.producer.NotificationProducer;
import org.example.pojo.dto.UserRequestDTO;
import org.example.pojo.responseDto.UserResponseDTO;
import org.example.userservice.model.UserDetails;
import org.example.userservice.repository.UserDetailsRepository;
import org.example.userservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;


@Service
@Slf4j
public class UserServiceImp implements UserService {

    @Autowired
    NotificationProducer notificationProducer;

    @Autowired
    UserDetailsRepository userDetailsRepository;


    @Override
    public UserResponseDTO saveUser(UserRequestDTO userRequestDTO) {
        UserDetails userDetails = null;
        if (CommonValidations.validateEnum(userRequestDTO.getRole(), RoleEnum.class)
                && validateUserName(userRequestDTO)) {
            userDetails = saveDetailsToDB(userRequestDTO);
        }
        return CommonUtils.convert(userDetails, UserResponseDTO.class);
    }


    private Boolean validateUserName(UserRequestDTO userRequestDTO) {
        log.info("Validating the UserName");
        if (CommonUtils.isValidString(userRequestDTO.getUserName())
                && CommonUtils.isValidString(userRequestDTO.getName())
                && CommonUtils.isValidString(userRequestDTO.getLastName())) {
            return true;
        }
        throw new DocumentorValidationException("Not a valid userName present");
    }


    private UserDetails saveDetailsToDB(UserRequestDTO userRequestDTO) {
        UserDetails userDetails = null;
        try {
            userDetails = new UserDetails();
            userDetails.setUserName(userRequestDTO.getUserName());
            userDetails.setName(userRequestDTO.getName());
            userDetails.setLastName(userRequestDTO.getLastName());
            userDetails.setRole(RoleEnum.valueOf(userRequestDTO.getRole()));
            userDetails.setMobileNo(userRequestDTO.getMobileNo());
            userDetails.setEmailId(userRequestDTO.getEmailId());
            userDetails.setUserStatusEnum(Objects.isNull(userRequestDTO.getUserStatusEnum())
                    ? UserStatusEnum.ACTIVE
                    : UserStatusEnum.valueOf(userRequestDTO.getUserStatusEnum()));
            userDetails.setCreatedAt(LocalDateTime.now());
            userDetails.setModifiedAt(null);
            userDetailsRepository.save(userDetails);
            //notificationProducer.publish(createUserTopic, UserRequestDTO.class.getName(), userDetails);

        } catch (Exception e) {
            throw new DocumentorException(e.getMessage());
        }
        return userDetails;
    }
}
