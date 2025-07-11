package com.dianatuman.practicum.transfer.service;

import com.dianatuman.practicum.transfer.dto.TransferDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TransferService {

    private final RestTemplate restTemplate;

    //    @Value("${bank-services.cash}")
    private final String accountsServiceURL = "http://accounts-service";
    //    @Value("${bank-services.cash}")
    private final String blockerServiceURL = "http://blocker-service";
    //    @Value("${bank-services.cash}")
    private final String notificationServiceURL = "http://notification-service";

    public TransferService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean transferAccount(TransferDTO transferDTO) {
        var isBlocked = Boolean.TRUE.equals(restTemplate
                .postForObject(blockerServiceURL + "/blocker", Math.abs(transferDTO.getCashSum()), Boolean.class));
        if (isBlocked) {
            return false;
        } else {

            //TODO go to exchange and check rates
            var isAccountOk = Boolean.TRUE.equals(restTemplate
                    .postForObject(accountsServiceURL + "/transfer", transferDTO, Boolean.class));
            restTemplate.postForObject(notificationServiceURL + "/transfer", isAccountOk, Boolean.class);
            return isAccountOk;
        }
    }
}
