package org.example.documentprocessingservice.serviceImp;


import lombok.extern.slf4j.Slf4j;
import org.example.documentprocessingservice.services.UserService;
import org.example.kafka.producer.NotificationProducer;
import org.example.pojo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImp implements UserService {

    @Autowired
    NotificationProducer notificationProducer;

    @Value("${dpms.kafka.topic.dsrms-store-user}")
    private String userTopicName;

    @Override
    public User saveUser(User userDetails) {
        notificationProducer.publish(userTopicName,User.class.getName(),"userDetails");
        log.info("sent to kafka topic: {}",userTopicName);
        return userDetails;
    }
}
