package com.dianatuman.practicum.exchange.controller;

import com.dianatuman.practicum.exchange.dto.CurrencyTransferDTO;
import com.dianatuman.practicum.exchange.dto.RatesDTO;
import com.dianatuman.practicum.exchange.service.ExchangeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("exchange")
public class ExchangeController {

    private final ExchangeService exchangeService;

    public ExchangeController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    @PostMapping
    public Float exchange(@RequestBody CurrencyTransferDTO currencyTransferDTO) {
        return exchangeService.exchange(currencyTransferDTO);
    }

    @GetMapping("rates")
    public RatesDTO getRates() {
        return exchangeService.getRates();
    }

    @PostMapping("rates")
    public void setRates(@RequestBody RatesDTO ratesDTO) {
        exchangeService.setRates(ratesDTO);
    }


}
