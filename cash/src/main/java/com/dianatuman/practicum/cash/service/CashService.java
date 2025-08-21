package com.dianatuman.practicum.cash.service;

import com.dianatuman.practicum.cash.dto.CashDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Slf4j
@Service
public class CashService {

    private final RestTemplate restTemplate;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final MeterRegistry meterRegistry;

    @Value("${accounts_service_url}")
    private String accountsServiceURL;
    @Value("${blocker_service_url}")
    private String blockerServiceURL;
    @Value("${bank_app_notifications_topic}")
    private String notificationsTopic;

    public CashService(RestTemplate restTemplate, KafkaTemplate<String, String> kafkaTemplate, MeterRegistry meterRegistry) {
        this.restTemplate = restTemplate;
        this.kafkaTemplate = kafkaTemplate;
        this.meterRegistry = meterRegistry;
    }

    public String cashAccount(CashDTO cashDTO) throws JsonProcessingException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();

        log.debug("{}/block", blockerServiceURL);
        var isBlocked = Boolean.TRUE.equals(restTemplate.postForObject(blockerServiceURL + "/block",
                Math.abs(cashDTO.getCashSum()), Boolean.class));
        if (isBlocked) {
            log.warn("Operation was blocked as suspicious");
            meterRegistry.counter("blocked_cash_operations",
                            "login", cashDTO.getAccountDTO().getUserLogin(),
                            "account_currency", cashDTO.getAccountDTO().getAccountCurrency())
                    .increment();
            return "Operation was blocked as suspicious.";
        } else {
            var jsonCashDTO = mapper.writeValueAsString(cashDTO);
            try {
                log.debug("{}/accounts/cash", accountsServiceURL);
                String s = restTemplate.postForObject(accountsServiceURL + "/accounts/cash",
                        new HttpEntity<>(jsonCashDTO, httpHeaders), String.class);
                if (Objects.equals(s, "OK")) {
                    kafkaTemplate.send(notificationsTopic, cashDTO.formMessage()).whenComplete((result, e) -> {
                        if (e != null) {
                            log.error("Ошибка при отправке сообщения: {}", e.getMessage(), e);
                            return;
                        }

                        RecordMetadata metadata = result.getRecordMetadata();
                        log.info("Сообщение отправлено. Topic = {}, partition = {}, offset = {}",
                                metadata.topic(), metadata.partition(), metadata.offset());
                    });
                }
                return s;
            } catch (Throwable e) {
                log.error(e.getMessage());
                return "Account service is not working. Please try later.";
            }
        }
    }
}