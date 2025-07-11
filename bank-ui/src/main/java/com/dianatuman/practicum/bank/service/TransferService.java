package com.dianatuman.practicum.bank.service;

import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@RefreshScope
@Service
public class TransferService {

    private final RestTemplate restTemplate;

    //    @Value("${bank-services.transfer}")
    private String transferServiceURL = "http://transfer-service";

    public TransferService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
}
