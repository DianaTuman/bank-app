package com.dianatuman.practicum.accounts.service;

import com.dianatuman.practicum.accounts.dto.AccountDTO;
import com.dianatuman.practicum.accounts.dto.CashDTO;
import com.dianatuman.practicum.accounts.dto.TransferDTO;
import com.dianatuman.practicum.accounts.entity.Account;
import com.dianatuman.practicum.accounts.entity.AccountId;
import com.dianatuman.practicum.accounts.mapper.AccountMapper;
import com.dianatuman.practicum.accounts.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public AccountService(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    public String cashAccount(CashDTO cashDTO) {
        AccountId accountId = accountMapper.toAccountId(cashDTO.getAccountDTO());
        Optional<Account> byId = accountRepository.findById(accountId);
        if (byId.isPresent()) {
            var account = byId.get();
            if (account.updateValue(cashDTO.getCashSum())) {
                accountRepository.save(account);
                log.info("{} user's {} account was cashed {}",
                        account.getUserLogin(), account.getAccountCurrency(), cashDTO.getCashSum());
                return "OK";
            } else {
                log.error("Not enough money on {} user's account {} to cash {}",
                        account.getUserLogin(), account.getAccountCurrency(), cashDTO.getCashSum());
                return "Not enough money on the account " + account.getAccountCurrency();
            }
        } else {
            log.error("Account not found {}", accountId);
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
                log.info("{} from {} account was transferred to {} account",
                        transferDTO.getAmountFrom(), transferDTO.getFromAccountDTO(), transferDTO.getToAccountDTO());
                return "OK";
            } else {
                log.error("Not enough money on {} user's account {} to transfer {}",
                        account1.getUserLogin(), account1.getAccountCurrency(), transferDTO.getAmountFrom());
                return "Not enough money on the account " + account1.getAccountCurrency();
            }
        } else {
            log.error("Account(s) not found.");
            return "Account(s) not found.";
        }
    }

    public void createAccount(AccountDTO accountDTO) {
        AccountId accountId = accountMapper.toAccountId(accountDTO);
        Optional<Account> byId = accountRepository.findById(accountId);
        if (byId.isEmpty()) {
            Account account = accountMapper.toAccount(accountDTO);
            account.setValue(0.0f);
            accountRepository.save(account);
            log.info("Account {} was created", accountDTO.toString());
        } else {
            log.warn("Account {} already exists", accountDTO.toString());
        }
    }

    public void deleteAccount(AccountDTO accountDTO) {
        AccountId accountId = accountMapper.toAccountId(accountDTO);
        Optional<Account> byId = accountRepository.findById(accountId);
        if (byId.isPresent()) {
            accountRepository.delete(byId.get());
            log.info("Account {} was deleted", accountDTO.toString());
        } else {
            log.warn("Account {} doesn't exists", accountDTO.toString());
        }
    }
}
