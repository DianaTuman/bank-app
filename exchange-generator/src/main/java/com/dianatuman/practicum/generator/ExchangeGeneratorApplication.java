package com.dianatuman.practicum.generator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableDiscoveryClient
public class ExchangeGeneratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExchangeGeneratorApplication.class, args);
    }

}
