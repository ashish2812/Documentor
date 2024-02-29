package org.example.userservice.services;


import org.example.pojo.dto.UserRequestDTO;
import org.example.pojo.responseDto.UserResponseDTO;

public interface UserService {

    UserResponseDTO saveUser(UserRequestDTO userDetails);
}
