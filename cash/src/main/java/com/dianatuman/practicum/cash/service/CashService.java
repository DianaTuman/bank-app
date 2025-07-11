package com.dianatuman.practicum.cash.service;

import com.dianatuman.practicum.cash.dto.CashDTO;
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

    public boolean cashAccount(CashDTO cashDTO) {
        var isBlocked = Boolean.TRUE.equals(restTemplate
                .postForObject(blockerServiceURL + "/blocker", Math.abs(cashDTO.getCashSum()), Boolean.class));
        if (isBlocked) {
            return false;
        } else {
            var isAccountOk = Boolean.TRUE.equals(restTemplate
                    .postForObject(accountsServiceURL + "/cash", cashDTO, Boolean.class));
            restTemplate.postForObject(notificationServiceURL + "/cash", isAccountOk, Boolean.class);
            return isAccountOk;
        }
    }
}