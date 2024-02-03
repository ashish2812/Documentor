package org.example.apigateway.logging;


import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
@Slf4j
public class LoggingConfig implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("Path of the request received: {}," +
                        "\n with id is: {}" +
                        "\n local address is: {}" +
                        "\n log prefix is: {}" +
                        "\n Response with status code is: {}",
                exchange.getRequest().getPath(),
                exchange.getRequest().getId(),
                exchange.getRequest().getLocalAddress(),
                exchange.getLogPrefix(),
                exchange.getResponse().getStatusCode());
        return chain.filter(exchange);
    }
}
