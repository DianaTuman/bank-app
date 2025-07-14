package com.dianatuman.practicum.notifications.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationsService {

    //    @Value("${bank-services.cash}")
    private final String bankUIURL = "http://bank-ui";

    private final RestTemplate restTemplate;

    public NotificationsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void sendNotification(String message) {
        restTemplate.postForObject(bankUIURL + "/api/notification", message, String.class);
    }
}
