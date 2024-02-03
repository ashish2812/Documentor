package org.example.documentprocessingservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dpm")
public class UserController {

    @GetMapping("/get")
    public String print(){
        return "hello";
    }
}
