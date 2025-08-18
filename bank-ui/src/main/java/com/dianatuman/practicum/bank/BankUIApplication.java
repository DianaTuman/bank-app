package com.dianatuman.practicum.bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class BankUIApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankUIApplication.class, args);
    }

}