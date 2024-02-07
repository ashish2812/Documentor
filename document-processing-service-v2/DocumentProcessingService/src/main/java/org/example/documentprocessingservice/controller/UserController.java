package org.example.documentprocessingservice.controller;

import lombok.extern.log4j.Log4j2;
import org.example.documentprocessingservice.serviceImp.KafkaServiceProducerImp;
import org.example.documentprocessingservice.services.KafkaServiceProducer;
import org.example.documentprocessingservice.services.UserService;
import org.example.pojo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/dpms")
@Log4j2
public class UserController {

    @Autowired
    KafkaServiceProducerImp kafkaServiceProducer;

    @Autowired
    UserService userService;

    @GetMapping("/get")
    public String print(){
        User user = new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setUserName("ashish2812");
        user.setRole("Admin");
        user.setLastName("Singh");
        user.setName("Ashish");
        User u = userService.saveUser(user);
        log.info("User created {}",u);


        return "Success";
    }
}
