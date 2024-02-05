package org.example.documentprocessingservice;

import org.example.documentprocessingservice.model.UserDetails;
import org.example.documentprocessingservice.repository.UserDetailsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class DocumentProcessingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DocumentProcessingServiceApplication.class, args);
    }

}
