package com.dianatuman.practicum.accounts.controller;

import com.dianatuman.practicum.accounts.dto.AccountDTO;
import com.dianatuman.practicum.accounts.dto.CashDTO;
import com.dianatuman.practicum.accounts.dto.TransferDTO;
import com.dianatuman.practicum.accounts.dto.UserDTO;
import com.dianatuman.practicum.accounts.service.AccountService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{login}")
    public UserDTO getAllAccountsForUser(@PathVariable String login) {
        return accountService.getAllAccountsByLogin(login);
    }

    @PostMapping("/{login}")
    public boolean createAccount(@RequestBody AccountDTO accountDTO) {
        return accountService.createAccount(accountDTO);
    }

    @DeleteMapping("/{login}")
    public boolean deleteAccount(@RequestBody AccountDTO accountDTO) {
        return accountService.deleteAccount(accountDTO);
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
