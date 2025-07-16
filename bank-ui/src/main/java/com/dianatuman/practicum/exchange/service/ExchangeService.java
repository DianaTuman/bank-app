package com.dianatuman.practicum.exchange.service;

import com.dianatuman.practicum.exchange.dto.CurrencyDTO;
import com.dianatuman.practicum.exchange.dto.RatesDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RefreshScope
public class ExchangeService {

    private final RestTemplate restTemplate;

    @Value("${bank-services.exchange}")
    private String exchangeServiceURL;

    public ExchangeService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<CurrencyDTO> getRates() {
        RatesDTO ratesDTO = restTemplate.getForObject(exchangeServiceURL + "/exchange/rates", RatesDTO.class);
        return ratesDTO.getCurrencyDTOS();
    }
}
