package com.dianatuman.practicum.cash.service;

import com.dianatuman.practicum.cash.dto.CashDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Slf4j
@Service
public class CashService {

    private final RestTemplate restTemplate;

    @Value("${accounts_service_url}")
    private String accountsServiceURL;
    @Value("${blocker_service_url}")
    private String blockerServiceURL;
    @Value("${notification_service_url}")
    private String notificationServiceURL;

    public CashService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String cashAccount(CashDTO cashDTO) throws JsonProcessingException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();

        log.info(blockerServiceURL + "/block");
        var isBlocked = Boolean.TRUE.equals(restTemplate.postForObject(blockerServiceURL + "/block",
                Math.abs(cashDTO.getCashSum()), Boolean.class));
        if (isBlocked) {
            return "Operation was blocked as suspicious.";
        } else {
            var jsonCashDTO = mapper.writeValueAsString(cashDTO);
            try {
                log.info(accountsServiceURL + "/accounts/cash");
                String s = restTemplate.postForObject(accountsServiceURL + "/accounts/cash",
                        new HttpEntity<>(jsonCashDTO, httpHeaders), String.class);
                if (Objects.equals(s, "OK")) {
                    log.info(blockerServiceURL + notificationServiceURL + "/notifications/cash");
                    restTemplate.postForLocation(notificationServiceURL + "/notifications/cash", cashDTO);
                }
                return s;
            } catch (Throwable e) {
                return "Account service is not working. Please try later.";
            }
        }
    }
}