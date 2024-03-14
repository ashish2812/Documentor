package org.example.userservice.controller;

import lombok.extern.log4j.Log4j2;
import org.example.base.commonDTO.ResponseDto;
import org.example.base.utils.CommonUtils;
import org.example.jwtutility.service.JwtService;
import org.example.pojo.dto.AuthRequestDTO;
import org.example.pojo.dto.JwtResponseDTO;
import org.example.pojo.dto.UserRequestDTO;
import org.example.pojo.responseDto.UserResponseDTO;
import org.example.userservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1/user")
@Log4j2
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    JwtService jwtService;


    @Autowired
    private AuthenticationManager authenticationManager;


    @PostMapping("/create")
    public ResponseDto<JwtResponseDTO> saveUserInfo(@RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO u = userService.saveUser(userRequestDTO);
        JwtResponseDTO jwtResponseDTO = new JwtResponseDTO();
        if(Objects.nonNull(u)){
            String token = jwtService.GenerateToken(userRequestDTO.getUserName());
            jwtResponseDTO = JwtResponseDTO.builder()
                    .accessToken(token)
                    .createdAt(CommonUtils.convertToLocalDateTimeViaInstant(jwtService.extractIssueDate(token)))
                    .expireAt(CommonUtils.convertToLocalDateTimeViaMilisecond(jwtService.extractExpiration(token)))
                    .build();
        }
        log.info("User created {}", u);
        return ResponseDto.success("User created successfully.", jwtResponseDTO);
    }

    @GetMapping("/get")
    public String suc() {
        return "Success";
    }

    @PostMapping("/login")
    public ResponseDto<JwtResponseDTO> AuthenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
        if (authentication.isAuthenticated()) {
            String token = jwtService.GenerateToken(authRequestDTO.getUsername());
            JwtResponseDTO jwtResponseDTO = JwtResponseDTO.builder()
                    .accessToken(token)
                    .createdAt(CommonUtils.convertToLocalDateTimeViaInstant(jwtService.extractIssueDate(token)))
                    .expireAt(CommonUtils.convertToLocalDateTimeViaMilisecond(jwtService.extractExpiration(token)))
                    .build();
            return ResponseDto.success("Logged In successfully at: " + jwtResponseDTO.getCreatedAt(), jwtResponseDTO);
        } else throw new UsernameNotFoundException("user not found");
    }
}
