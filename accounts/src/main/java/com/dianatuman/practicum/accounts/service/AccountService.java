package com.dianatuman.practicum.accounts.service;

import com.dianatuman.practicum.accounts.dto.AccountDTO;
import com.dianatuman.practicum.accounts.dto.CashDTO;
import com.dianatuman.practicum.accounts.dto.TransferDTO;
import com.dianatuman.practicum.accounts.entity.Account;
import com.dianatuman.practicum.accounts.entity.AccountId;
import com.dianatuman.practicum.accounts.entity.User;
import com.dianatuman.practicum.accounts.mapper.AccountMapper;
import com.dianatuman.practicum.accounts.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public AccountService(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    public List<Account> getAllAccountsByLogin(String login) {
        User user = new User();
        user.setLogin(login);
        return accountRepository.findByUserLogin(user);
    }

    public boolean cashAccount(CashDTO cashDTO) {
        AccountId accountId = accountMapper.toAccountId(cashDTO.getAccountDTO());
        Optional<Account> byId = accountRepository.findById(accountId);
        if (byId.isPresent()) {
            var account = byId.get();
            return account.updateValue(cashDTO.getCashSum());
        } else {
            return false;
        }
    }

    public boolean transferAccount(TransferDTO transferDTO) {
        AccountId fromAccountId = accountMapper.toAccountId(transferDTO.getToAccountDTO());
        Optional<Account> fromAccount = accountRepository.findById(fromAccountId);
        AccountId toAccountId = accountMapper.toAccountId(transferDTO.getToAccountDTO());
        Optional<Account> toAccount = accountRepository.findById(toAccountId);
        if (fromAccount.isPresent() && toAccount.isPresent()) {
            var account1 = fromAccount.get();
            var account2 = toAccount.get();
            if (account1.updateValue(-transferDTO.getAmountFrom())) {
                return account2.updateValue(transferDTO.getAmountTo());
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean createAccount(AccountDTO accountDTO) {
        AccountId accountId = accountMapper.toAccountId(accountDTO);
        Optional<Account> byId = accountRepository.findById(accountId);
        if (byId.isPresent()) {
            return false;
        } else {
            accountRepository.save(accountMapper.toAccount(accountDTO));
            return true;
        }
    }

    public boolean deleteAccount(AccountDTO accountDTO) {
        AccountId accountId = accountMapper.toAccountId(accountDTO);
        Optional<Account> byId = accountRepository.findById(accountId);
        if (byId.isPresent()) {
            accountRepository.delete(byId.get());
            return true;
        } else {
            return false;
        }
    }
}
