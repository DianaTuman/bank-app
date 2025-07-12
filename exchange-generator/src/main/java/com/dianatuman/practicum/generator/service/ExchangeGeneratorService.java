package com.dianatuman.practicum.generator.service;

import com.dianatuman.practicum.generator.dto.CurrencyDTO;
import com.dianatuman.practicum.generator.dto.RatesDTO;
import com.dianatuman.practicum.generator.enums.BankCurrency;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RefreshScope
public class ExchangeGeneratorService {

    private final RestTemplate restTemplate;

    //    @Value("${bank-services.cash}")
    private final String exchangeServiceURL = "http://exchange-service";

    public ExchangeGeneratorService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Scheduled(cron = "* * * * * *")
    public void generateExchange() throws JsonProcessingException {
        List<CurrencyDTO> currencyDTOS = new ArrayList<>();
        Random rand = new Random();
        currencyDTOS.add(new CurrencyDTO(BankCurrency.RUB, 1.0F));
        currencyDTOS.add(new CurrencyDTO(BankCurrency.USD, rand.nextFloat(0.01F, 0.02F)));
        currencyDTOS.add(new CurrencyDTO(BankCurrency.CNY, rand.nextFloat(0.08F, 0.1F)));

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper();
        var json = mapper.writeValueAsString(new RatesDTO(currencyDTOS));
        HttpEntity<String> request = new HttpEntity<>(json, httpHeaders);

        restTemplate.postForObject(exchangeServiceURL + "/exchange/rates", request, Boolean.class);
    }
}
