package com.dianatuman.practicum.gateway.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfiguration {


    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("accounts_route", r -> r
                        .path("/accounts/**")
                        .filters(f ->
                                f.circuitBreaker(c -> c.setName("exampleCircuitBreaker")
                                        .setFallbackUri("/fallback")))
                        .uri("lb://accounts-service"))
                .route("cash_route", r -> r
                        .path("/cash/**")
                        .filters(f ->
                                f.circuitBreaker(c -> c.setName("exampleCircuitBreaker")
                                        .setFallbackUri("/fallback")))
                        .uri("lb://cash-service"))
                .route("exchange_route", r -> r
                        .path("/exchange/**")
                        .filters(f ->
                                f.circuitBreaker(c -> c.setName("exampleCircuitBreaker")
                                        .setFallbackUri("/fallback")))
                        .uri("lb://exchange-service"))
                .route("transfer_route", r -> r
                        .path("/transfer/**")
                        .filters(f ->
                                f.circuitBreaker(c -> c.setName("exampleCircuitBreaker")
                                        .setFallbackUri("/fallback")))
                        .uri("lb://transfer-service"))
                .build();
    }

}
