package com.dianatuman.practicum.accounts.controller;

import com.dianatuman.practicum.accounts.dto.CashDTO;
import com.dianatuman.practicum.accounts.dto.TransferDTO;
import com.dianatuman.practicum.accounts.entity.Account;
import com.dianatuman.practicum.accounts.service.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{login}")
    public List<Account> getAllAccountsForUser(@PathVariable String login) {
        List<Account> allAccounts = accountService.getAllAccounts();
        return allAccounts;
    }

    @PostMapping("/cash")
    public boolean cashAccount(@RequestBody CashDTO cashDTO) {
        return accountService.cashAccount(cashDTO);
    }

    @PostMapping("/transfer")
    public boolean transferAccount(@RequestBody TransferDTO transferDTO) {
        return accountService.transferAccount(transferDTO);
    }
}
