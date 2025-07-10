package com.dianatuman.practicum.accounts.repository;

import com.dianatuman.practicum.accounts.entity.Account;
import com.dianatuman.practicum.accounts.entity.AccountId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, AccountId> {
}
