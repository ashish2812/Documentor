package org.example.documentprocessingservice;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
@ComponentScan(basePackages = { "org.example" })
public class DocumentProcessingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DocumentProcessingServiceApplication.class, args);
    }

}
