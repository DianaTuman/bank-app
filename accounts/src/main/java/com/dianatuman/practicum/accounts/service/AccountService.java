package com.dianatuman.practicum.accounts.service;

import com.dianatuman.practicum.accounts.dto.AccountDTO;
import com.dianatuman.practicum.accounts.dto.CashDTO;
import com.dianatuman.practicum.accounts.dto.TransferDTO;
import com.dianatuman.practicum.accounts.dto.UserDTO;
import com.dianatuman.practicum.accounts.entity.Account;
import com.dianatuman.practicum.accounts.entity.AccountId;
import com.dianatuman.practicum.accounts.entity.User;
import com.dianatuman.practicum.accounts.mapper.AccountMapper;
import com.dianatuman.practicum.accounts.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public AccountService(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    public UserDTO getAllAccountsByLogin(String login) {
        User user = new User();
        user.setLogin(login);
        UserDTO result = new UserDTO();
        result.setAccounts(accountRepository.findByUserLogin(user).stream().map(accountMapper::toDTO).toList());
        return result;
    }

    public String cashAccount(CashDTO cashDTO) {
        AccountId accountId = accountMapper.toAccountId(cashDTO.getAccountDTO());
        Optional<Account> byId = accountRepository.findById(accountId);
        if (byId.isPresent()) {
            var account = byId.get();
            if (account.updateValue(cashDTO.getCashSum())) {
                accountRepository.save(account);
                return "OK";
            } else {
                return "Not enough money on the account " + account.getAccountCurrency();
            }
        } else {
            return "Account not found.";
        }
    }

    public String transferAccount(TransferDTO transferDTO) {
        AccountId fromAccountId = accountMapper.toAccountId(transferDTO.getFromAccountDTO());
        Optional<Account> fromAccount = accountRepository.findById(fromAccountId);
        AccountId toAccountId = accountMapper.toAccountId(transferDTO.getToAccountDTO());
        Optional<Account> toAccount = accountRepository.findById(toAccountId);
        if (fromAccount.isPresent() && toAccount.isPresent()) {
            var account1 = fromAccount.get();
            var account2 = toAccount.get();
            if (account1.updateValue(-transferDTO.getAmountFrom())) {
                accountRepository.save(account1);
                account2.updateValue(transferDTO.getAmountTo());
                accountRepository.save(account2);
                return "OK";
            } else {
                return "Not enough money on the account " + account1.getAccountCurrency();
            }
        } else {
            return "Account(s) not found.";
        }
    }

    public void createAccount(AccountDTO accountDTO) {
        AccountId accountId = accountMapper.toAccountId(accountDTO);
        Optional<Account> byId = accountRepository.findById(accountId);
        if (byId.isEmpty()) {
            accountRepository.save(accountMapper.toAccount(accountDTO));
        }
    }

    public void deleteAccount(AccountDTO accountDTO) {
        AccountId accountId = accountMapper.toAccountId(accountDTO);
        Optional<Account> byId = accountRepository.findById(accountId);
        byId.ifPresent(accountRepository::delete);
    }
}
