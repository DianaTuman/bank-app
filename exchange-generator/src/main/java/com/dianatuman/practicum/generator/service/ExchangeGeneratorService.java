package com.dianatuman.practicum.generator.service;

import com.dianatuman.practicum.generator.dto.CurrencyDTO;
import com.dianatuman.practicum.generator.enums.BankCurrency;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


@Service
public class ExchangeGeneratorService {

    @Scheduled(cron = "0 * * * * *")
    public void generateExchange() {
        List<CurrencyDTO> currencyDTOS = new ArrayList<>();
        Random rand = new Random();
        currencyDTOS.add(new CurrencyDTO(BankCurrency.RUB, 1.0F));
        currencyDTOS.add(new CurrencyDTO(BankCurrency.USD, rand.nextFloat(0.01F, 0.02F)));
        currencyDTOS.add(new CurrencyDTO(BankCurrency.CNY, rand.nextFloat(0.08F, 0.1F)));

        //send currencyDTO to exchange
    }
}
