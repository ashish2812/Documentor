package org.example.datastoreretrieveservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = { "org.example" })
public class DataStoreRetrieveServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataStoreRetrieveServiceApplication.class, args);
    }

}
