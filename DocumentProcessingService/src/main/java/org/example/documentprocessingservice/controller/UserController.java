package org.example.documentprocessingservice.controller;

import org.example.documentprocessingservice.services.KafkaServiceProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dpm")
public class UserController {

    @Autowired
    KafkaServiceProducer kafkaServiceProducer;

    @GetMapping("/get")
    public String print(){
        for(int i=0;i<10;i++){
            kafkaServiceProducer.sentMessage("Message "+ i);
        }
        return "Success";
    }
}
