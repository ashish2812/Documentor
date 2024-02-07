package org.example.datastoreretrieveservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "org.example" })
public class DataStoreRetrieveServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataStoreRetrieveServiceApplication.class, args);
    }

}
