package com.dianatuman.practicum.accounts.controller;

import com.dianatuman.practicum.accounts.dto.CashDTO;
import com.dianatuman.practicum.accounts.dto.TransferDTO;
import com.dianatuman.practicum.accounts.service.AccountService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/cash")
    public String cashAccount(@RequestBody CashDTO cashDTO) {
        return accountService.cashAccount(cashDTO);
    }

    @PostMapping("/transfer")
    public String transferAccount(@RequestBody TransferDTO transferDTO) {
        return accountService.transferAccount(transferDTO);
    }
}
