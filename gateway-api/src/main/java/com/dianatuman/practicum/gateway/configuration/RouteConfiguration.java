package com.dianatuman.practicum.gateway.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfiguration {

    @Value("${accounts_service_url}")
    private String accountsServiceURL;
    @Value("${cash_service_url}")
    private String cashServiceURL;
    @Value("${transfer_service_url}")
    private String transferServiceURL;
    @Value("${exchange_service_url}")
    private String exchangeServiceURL;

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("accounts_route", r -> r
                        .path("/accounts/**")
                        .filters(f ->
                                f.circuitBreaker(c -> c.setName("exampleCircuitBreaker")
                                        .setFallbackUri("/fallback")))
                        .uri(accountsServiceURL))
                .route("cash_route", r -> r
                        .path("/cash/**")
                        .filters(f ->
                                f.circuitBreaker(c -> c.setName("exampleCircuitBreaker")
                                        .setFallbackUri("/fallback")))
                        .uri(cashServiceURL))
                .route("exchange_route", r -> r
                        .path("/exchange/**")
                        .filters(f ->
                                f.circuitBreaker(c -> c.setName("exampleCircuitBreaker")
                                        .setFallbackUri("/fallback")))
                        .uri(exchangeServiceURL))
                .route("transfer_route", r -> r
                        .path("/transfer/**")
                        .filters(f ->
                                f.circuitBreaker(c -> c.setName("exampleCircuitBreaker")
                                        .setFallbackUri("/fallback")))
                        .uri(transferServiceURL))
                .build();
    }

}
