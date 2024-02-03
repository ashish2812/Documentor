package org.example.apigateway.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class ApiLocator {

    @Bean
    public RouteLocator getwayRouter(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                        .path("/dpm/**") //url for the currency-exchange
                        .uri("lb://Document-Processing-Service") //LoadBalancer of currency-exchange that are the instances registered in eureka server
                )
                .build();
    }
}
