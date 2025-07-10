package com.dianatuman.practicum.accounts.service;

import com.dianatuman.practicum.accounts.dto.CashDTO;
import com.dianatuman.practicum.accounts.dto.TransferDTO;
import com.dianatuman.practicum.accounts.entity.Account;
import com.dianatuman.practicum.accounts.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public boolean cashAccount(CashDTO cashDTO) {

        return false;
    }

    public boolean transferAccount(TransferDTO transferDTO) {

        return false;
    }
}
