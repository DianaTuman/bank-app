package com.dianatuman.practicum.exchange.controller;

import com.dianatuman.practicum.exchange.dto.CurrencyDTO;
import com.dianatuman.practicum.exchange.service.ExchangeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class ApiController {

    private final ExchangeService exchangeService;

    private String notification = "";

    public ApiController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    @GetMapping("rates")
    public List<CurrencyDTO> getRates() {
        return exchangeService.getRates();
    }

    @GetMapping("notification")
    public String getNotification() {
        if (notification.isBlank())
            return "";
        else {
            var temp = "NEW \n" + notification;
            notification = "";
            return temp;
        }
    }

    @PostMapping("notification")
    public void receiveNotification(@RequestBody String notification) {
        this.notification = notification;
    }
}
