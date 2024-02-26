package org.example.datastoreretrieveservice;

import org.example.datastoreretrieveservice.model.UserDetails;
import org.example.pojo.dto.UserRequestDTO;
import org.example.pojo.model.User;
import org.springframework.stereotype.Service;


public interface UserServiceAndRetrive {

    UserDetails createUser(UserRequestDTO userRequestDTO);

}
