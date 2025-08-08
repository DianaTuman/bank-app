package com.dianatuman.practicum.notifications.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class NotificationsService {

    @Value("${bank_ui_url}")
    private String bankUIURL;

    private final RestTemplate restTemplate;

    public NotificationsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @KafkaListener(topics = "${spring.kafka.topics}")
    public void listen(String data) throws JsonProcessingException {
        log.info("Получены данные из топика: {}", data);
        restTemplate.postForObject(bankUIURL + "/api/notification", data, String.class);
    }
}
