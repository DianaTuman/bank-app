package com.dianatuman.practicum.bank.service;

import com.dianatuman.practicum.bank.dto.AccountDTO;
import com.dianatuman.practicum.bank.dto.CashDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@RefreshScope
@Service
public class CashService {

    private final RestTemplate restTemplate;

    public CashService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String cash(String login, String currency, String action, Float value) throws JsonProcessingException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();

        CashDTO cashDTO = new CashDTO(new AccountDTO(login, currency));
        if ("PUT".equals(action)) {
            cashDTO.setCashSum(value);
        } else if ("GET".equals(action)) {
            cashDTO.setCashSum(-value);
        } else {
            return "Action not supported.";
        }

        var jsonCashDTO = mapper.writeValueAsString(cashDTO);
        try {
            return restTemplate.postForObject("/cash",
                    new HttpEntity<>(jsonCashDTO, httpHeaders), String.class);
        } catch (Throwable e) {
            return "Cash service is not working. Please try later.";
        }
    }
}
