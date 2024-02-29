package org.example.userservice.controller;

import lombok.extern.log4j.Log4j2;
import org.example.base.commonDTO.ResponseDto;
import org.example.base.exceptions.DocumentorException;
import org.example.pojo.dto.UserRequestDTO;
import org.example.pojo.responseDto.UserResponseDTO;
import org.example.userservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@Log4j2
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/create")
    public ResponseDto<UserResponseDTO> saveUserInfo(@RequestBody UserRequestDTO userRequestDTO) {
        try {
            UserResponseDTO u = userService.saveUser(userRequestDTO);
            log.info("User created {}", u);
            return ResponseDto.success("User created successfully.", u);
        } catch (DocumentorException documentorException) {
            log.error("Error in Creating User, Error is ", documentorException);
            return ResponseDto.failure(documentorException.getMessage());
        } catch (Exception e) {
            log.error("Error in Create User, error is ", e);
            return ResponseDto.failure(e.getMessage());
        }
    }

    @GetMapping("/get")
    public String suc(){
        return "Success";
    }
}