package com.dianatuman.practicum.notifications.service;

import io.micrometer.core.instrument.MeterRegistry;
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
    private final MeterRegistry meterRegistry;

    public NotificationsService(RestTemplate restTemplate, MeterRegistry meterRegistry) {
        this.restTemplate = restTemplate;
        this.meterRegistry = meterRegistry;
    }

    @KafkaListener(topics = "${spring.kafka.topics}")
    public void listen(String data) {
        log.info("Получены данные из топика: {}", data);
        log.debug("{}/api/notification", bankUIURL);
        try {
            restTemplate.postForObject(bankUIURL + "/api/notification", data, String.class);
        } catch (Throwable e) {
            log.error(e.getMessage());
            meterRegistry.counter("failed_notifications").increment();
        }
    }
}
