package org.example.documentprocessingservice.controller;

import lombok.extern.log4j.Log4j2;
import org.example.base.commonDTO.ResponseDto;
import org.example.base.enums.RoleEnum;
import org.example.base.exceptions.DocumentorException;
import org.example.documentprocessingservice.serviceImp.KafkaServiceProducerImp;
import org.example.documentprocessingservice.services.KafkaServiceProducer;
import org.example.documentprocessingservice.services.UserService;
import org.example.pojo.dto.UserRequestDTO;
import org.example.pojo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/dpms")
@Log4j2
public class UserController {

    @Autowired
    KafkaServiceProducerImp kafkaServiceProducer;

    @Autowired
    UserService userService;

    @PostMapping("/create/user")
    public ResponseDto<UserRequestDTO> saveUserInfo(@RequestBody UserRequestDTO userRequestDTO){
        try {
            UserRequestDTO u = userService.saveUser(userRequestDTO);
            log.info("User created {}",u);
            return ResponseDto.success("User created successfully.", u);
        }catch (DocumentorException documentorException){
            log.error("Error in Creating User, Error is ", documentorException);
            return ResponseDto.failure(documentorException.getMessage());
        }catch (Exception e) {
            log.error("Error in Create User, error is ", e);
            return ResponseDto.failure(e.getMessage());
        }
    }
}
