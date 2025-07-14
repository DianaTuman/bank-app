package com.dianatuman.practicum.cash.service;

import com.dianatuman.practicum.cash.dto.CashDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CashService {

    private final RestTemplate restTemplate;

    //    @Value("${bank-services.cash}")
    private final String accountsServiceURL = "http://accounts-service";
    //    @Value("${bank-services.cash}")
    private final String blockerServiceURL = "http://blocker-service";
    //    @Value("${bank-services.cash}")
    private final String notificationServiceURL = "http://notification-service";

    public CashService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String cashAccount(CashDTO cashDTO) throws JsonProcessingException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();

        var isBlocked = Boolean.TRUE.equals(restTemplate.postForObject(blockerServiceURL + "/block",
                Math.abs(cashDTO.getCashSum()), Boolean.class));
        if (isBlocked) {
            return "Operation was blocked as suspicious.";
        } else {
            var jsonCashDTO = mapper.writeValueAsString(cashDTO);
            try {
                return restTemplate.postForObject(accountsServiceURL + "/accounts/cash",
                        new HttpEntity<>(jsonCashDTO, httpHeaders), String.class);
//            restTemplate.postForObject(notificationServiceURL + "/cash", isAccountOk, Boolean.class);
            } catch (Throwable e) {
                return "Account service is not working. Please try later.";
            }
        }
    }
}