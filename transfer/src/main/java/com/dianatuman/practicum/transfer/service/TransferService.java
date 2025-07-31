package com.dianatuman.practicum.transfer.service;

import com.dianatuman.practicum.transfer.dto.CurrencyTransferDTO;
import com.dianatuman.practicum.transfer.dto.TransferDTO;
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
public class TransferService {

    private final RestTemplate restTemplate;

    @Value("${accounts_service_url}")
    private String accountsServiceURL;
    @Value("${blocker_service_url}")
    private String blockerServiceURL;
    @Value("${notification_service_url}")
    private String notificationServiceURL;
    @Value("${exchange_service_url}")
    private String exchangeServiceURL;

    public TransferService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String transferAccount(TransferDTO transferDTO) throws JsonProcessingException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();

        log.info(blockerServiceURL + "/block");
        boolean isBlocked = Boolean.TRUE.equals(restTemplate
                .postForObject(blockerServiceURL + "/block", Math.abs(transferDTO.getAmountFrom()), Boolean.class));
        if (isBlocked) {
            return "Operation was blocked as suspicious.";
        } else {
            CurrencyTransferDTO currencyTransferDTO = new CurrencyTransferDTO(transferDTO);
            var jsonCurrencyTransferDTO = mapper.writeValueAsString(currencyTransferDTO);
            Float amountTo;
            try {
                log.info(exchangeServiceURL + "/exchange");
                amountTo = restTemplate.postForObject(exchangeServiceURL + "/exchange",
                        new HttpEntity<>(jsonCurrencyTransferDTO, httpHeaders), Float.class);
            } catch (Throwable e) {
                return "Exchange service is not working. Please try later.";
            }
            transferDTO.setAmountTo(amountTo);
            var jsonTransferDTO = mapper.writeValueAsString(transferDTO);
            try {
                log.info(accountsServiceURL + "/accounts/transfer");
                String s = restTemplate.postForObject(accountsServiceURL + "/accounts/transfer",
                        new HttpEntity<>(jsonTransferDTO, httpHeaders), String.class);
                if (Objects.equals(s, "OK")) {
                    log.info(blockerServiceURL + notificationServiceURL + "/notifications/transfer");
                    restTemplate.postForLocation(notificationServiceURL + "/notifications/transfer", transferDTO);
                }
                return s;
            } catch (Throwable e) {
                return "Account service is not working. Please try later.";
            }
        }
    }
}
