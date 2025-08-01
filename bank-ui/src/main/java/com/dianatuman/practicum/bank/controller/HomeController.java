package com.dianatuman.practicum.bank.controller;

import com.dianatuman.practicum.bank.dto.AccountCurrencyDTO;
import com.dianatuman.practicum.bank.dto.AccountDTO;
import com.dianatuman.practicum.bank.dto.CurrencyDTO;
import com.dianatuman.practicum.bank.dto.UserDTO;
import com.dianatuman.practicum.bank.service.AccountsService;
import com.dianatuman.practicum.bank.service.ExchangeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller("/")
public class HomeController {

    private final AccountsService accountsService;
    private final ExchangeService exchangeService;

    public HomeController(AccountsService accountsService, ExchangeService exchangeService) {
        this.accountsService = accountsService;
        this.exchangeService = exchangeService;
    }

    @GetMapping
    public String homePage() {
        return "redirect:./main";
    }

    @GetMapping("/main")
    public String mainPage(Model model, Principal principal) {
        UserDTO userDTO = accountsService.getUserByLogin(principal.getName());
        model.addAttribute("login", userDTO.getLogin());
        model.addAttribute("name", userDTO.getName());
        model.addAttribute("birthdate",
                Instant.ofEpochMilli(userDTO.getBirthdate()).atZone(ZoneId.systemDefault()).toLocalDate());
        model.addAttribute("users", accountsService.getAllUsers());
        List<CurrencyDTO> rates;
        try {
            rates = exchangeService.getRates();
        } catch (Throwable e) {
            rates = List.of();
        }
        model.addAttribute("currency", rates);
        model.addAttribute("accounts", combineRatesAndAccount(rates, userDTO.getAccounts()));
        return "main.html";
    }

    private List<AccountCurrencyDTO> combineRatesAndAccount(List<CurrencyDTO> rates, List<AccountDTO> accounts) {
        List<AccountCurrencyDTO> result = new ArrayList<>();
        if (rates != null) {
            for (CurrencyDTO currencyDTO : rates) {
                Optional<AccountDTO> first = accounts.stream()
                        .filter(accountDTO -> accountDTO.getAccountCurrency().equals(currencyDTO.getName()))
                        .findFirst();
                result.add(new AccountCurrencyDTO(currencyDTO, first.map(AccountDTO::getValue).orElse(0.0f), first.isPresent()));
            }
        }
        return result;
    }
}
