package com.dianatuman.practicum.bank.service;

import com.dianatuman.practicum.bank.dto.AccountDTO;
import com.dianatuman.practicum.bank.dto.TransferDTO;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@RefreshScope
@Service
public class TransferService {

    private final RestTemplate restTemplate;

    //    @Value("${bank-services.transfer}")
    private String transferServiceURL = "http://transfer-service";

    public TransferService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean transfer(String login, String fromCurrency, Float value, String toLogin, String toCurrency) {
        TransferDTO transferDTO = new TransferDTO();
        transferDTO.setFromAccountDTO(new AccountDTO(login, fromCurrency));
        transferDTO.setToAccountDTO(new AccountDTO(toLogin, toCurrency));
        transferDTO.setAmountFrom(value);

        return Boolean.TRUE.equals(restTemplate
                .postForObject(transferServiceURL + "/transfer", transferDTO, Boolean.class));
    }
}
