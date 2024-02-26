package org.example.documentprocessingservice.serviceImp;


import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.example.base.enums.RoleEnum;
import org.example.base.enums.UserStatusEnum;
import org.example.base.exceptions.DocumentorValidationException;
import org.example.base.utils.CommonUtils;
import org.example.documentprocessingservice.services.UserService;
import org.example.kafka.producer.NotificationProducer;
import org.example.pojo.dto.UserRequestDTO;
import org.example.pojo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class UserServiceImp implements UserService {

    private Set<RoleEnum> roleEnums;
    private Set<UserStatusEnum> userStatusEnums;

    @PostConstruct
    private void fillAllEnumsIntoList(){
        log.info("Running post Construct");

        roleEnums = CommonUtils.enumValuesInList(RoleEnum.class);
        log.info("Added roles, size: {}", roleEnums.size());

        userStatusEnums = CommonUtils.enumValuesInList(UserStatusEnum.class);
        log.info("Added UserStatusEnum, size: {}", userStatusEnums.size());

    }


    @Autowired
    NotificationProducer notificationProducer;

    @Value("${dpms.kafka.topic.dsrms-create-user}")
    private String createUserTopic;

    @Override
    public UserRequestDTO saveUser(UserRequestDTO userDetails) {

        if(validateEnum(userDetails) && validateUserName(userDetails)) {
            notificationProducer.publish(createUserTopic, UserRequestDTO.class.getName(), userDetails);
            log.info("sent to kafka topic: {}", createUserTopic);
        }
        return userDetails;
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

    private Boolean validateEnum(UserRequestDTO userRequestDTO) {
        log.info("Validating the Enums");
        if (CommonUtils.isValidString(userRequestDTO.getRole())
                && roleEnums.contains(RoleEnum.valueOf(userRequestDTO.getRole()))) {
            return true;
        }
        log.info("Validation failed for enum");
        throw new DocumentorValidationException("Not a valid Enum present");
    }
}
