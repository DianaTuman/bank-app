package com.dianatuman.practicum.bank.service;

import com.dianatuman.practicum.bank.dto.CurrencyDTO;
import com.dianatuman.practicum.bank.dto.RatesDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RefreshScope
public class ExchangeService {

    private final RestTemplate restTemplate;

    @Value("${bank-services.gateway-api}")
    private String gatewayURL;

    public ExchangeService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<CurrencyDTO> getRates() {
        RatesDTO ratesDTO = restTemplate.getForObject(gatewayURL + "/exchange/rates", RatesDTO.class);
        return ratesDTO.getCurrencyDTOS();
    }
}
