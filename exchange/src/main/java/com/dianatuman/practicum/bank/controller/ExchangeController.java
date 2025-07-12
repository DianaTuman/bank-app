package com.dianatuman.practicum.bank.controller;

import com.dianatuman.practicum.bank.dto.CurrencyTransferDTO;
import com.dianatuman.practicum.bank.dto.RatesDTO;
import com.dianatuman.practicum.bank.service.ExchangeService;
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
