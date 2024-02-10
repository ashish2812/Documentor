package org.example.documentprocessingservice.serviceImp;


import lombok.extern.slf4j.Slf4j;
import org.example.documentprocessingservice.services.UserService;
import org.example.kafka.producer.NotificationProducer;
import org.example.pojo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class UserServiceImp implements UserService {

    @Autowired
    NotificationProducer notificationProducer;

    @Value("${dpms.kafka.topic.dsrms-store-user}")
    private String userTopicName;

    @Override
    public User saveUser(User userDetails) {
        for(int i =0;i<10;i++) {
            notificationProducer.publish(userTopicName, User.class.getName(),
//                User.builder()
//                        .userId(UUID.randomUUID().toString())
//                        .userName("Ashish2812")
//                        .emailId("ashish@7612")
//                        .mobileNo("563521322")
//                .build()
                    "update "+i
            );
        }
        log.info("sent to kafka topic: {}",userTopicName);
        return userDetails;
    }
}
