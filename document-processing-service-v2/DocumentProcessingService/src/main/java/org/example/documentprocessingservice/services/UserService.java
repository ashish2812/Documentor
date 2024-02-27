package org.example.documentprocessingservice.services;


import org.example.pojo.dto.UserRequestDTO;
import org.example.pojo.model.User;

public interface UserService {

    UserRequestDTO saveUser(UserRequestDTO userDetails);
}
