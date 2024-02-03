package org.example.documentprocessingservice;

import org.example.documentprocessingservice.model.UserDetails;
import org.example.documentprocessingservice.repository.UserDetailsRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DocumentProcessingServiceApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext cc = SpringApplication.run(DocumentProcessingServiceApplication.class, args);
        UserDetailsRepository udr = cc.getBean(UserDetailsRepository.class);
        UserDetails userDetails = new UserDetails();
        userDetails.setUserName("Ashish");
        udr.save(userDetails);
    }

}
