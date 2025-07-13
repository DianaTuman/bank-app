package com.dianatuman.practicum.transfer.service;

import com.dianatuman.practicum.transfer.dto.CurrencyTransferDTO;
import com.dianatuman.practicum.transfer.dto.TransferDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TransferService {

    private final RestTemplate restTemplate;

    //    @Value("${bank-services.cash}")
    private final String accountsServiceURL = "http://accounts-service";
    //    @Value("${bank-services.cash}")
    private final String exchangeServiceURL = "http://exchange-service";
    //    @Value("${bank-services.cash}")
    private final String blockerServiceURL = "http://blocker-service";
    //    @Value("${bank-services.cash}")
    private final String notificationServiceURL = "http://notification-service";

    public TransferService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String transferAccount(TransferDTO transferDTO) throws JsonProcessingException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();


        boolean isBlocked = Boolean.TRUE.equals(restTemplate
                .postForObject(blockerServiceURL + "/block", Math.abs(transferDTO.getAmountFrom()), Boolean.class));
        if (isBlocked) {
            return "Operation was blocked as suspicious.";
        } else {
            CurrencyTransferDTO currencyTransferDTO = new CurrencyTransferDTO(transferDTO);
            var jsonCurrencyTransferDTO = mapper.writeValueAsString(currencyTransferDTO);
            Float amountTo;
            try {
                amountTo = restTemplate.postForObject(exchangeServiceURL + "/exchange",
                        new HttpEntity<>(jsonCurrencyTransferDTO, httpHeaders), Float.class);
            } catch (Throwable e) {
                return "Exchange service is not working. Please try later.";
            }
            transferDTO.setAmountTo(amountTo);
            var jsonTransferDTO = mapper.writeValueAsString(transferDTO);
            try {
                return restTemplate.postForObject(accountsServiceURL + "/accounts/transfer",
                        new HttpEntity<>(jsonTransferDTO, httpHeaders), String.class);
                //            restTemplate.postForObject(notificationServiceURL + "/transfer", isAccountOk, Boolean.class);
            } catch (Throwable e) {
                return "Account service is not working. Please try later.";
            }
        }
    }
}
