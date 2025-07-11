package com.dianatuman.practicum.bank.service;

import com.dianatuman.practicum.bank.dto.AccountDTO;
import com.dianatuman.practicum.bank.dto.CashDTO;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@RefreshScope
@Service
public class CashService {

    private final RestTemplate restTemplate;

    //    @Value("${bank-services.cash}")
    private String cashServiceURL = "http://cash-service";

    public CashService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean cash(String login, String currency, String action, Float value) {
        CashDTO cashDTO = new CashDTO(new AccountDTO(login, currency));
        if ("PUT".equals(action)) {
            cashDTO.setCashSum(value);
        } else if ("GET".equals(action)) {
            cashDTO.setCashSum(-value);
        } else {
            return false;
        }
        return Boolean.TRUE.equals(restTemplate
                .postForObject(cashServiceURL + "/cash", cashDTO, Boolean.class));
    }
}
