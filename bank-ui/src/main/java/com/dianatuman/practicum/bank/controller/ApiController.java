package com.dianatuman.practicum.bank.controller;

import com.dianatuman.practicum.bank.dto.CurrencyDTO;
import com.dianatuman.practicum.bank.service.ExchangeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api")
public class ApiController {

    private final ExchangeService exchangeService;

    public ApiController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    @GetMapping("rates")
    public List<CurrencyDTO> getRates() {
        return exchangeService.getRates();
    }
}
