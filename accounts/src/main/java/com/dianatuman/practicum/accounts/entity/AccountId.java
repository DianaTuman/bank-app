package com.dianatuman.practicum.accounts.entity;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@EqualsAndHashCode
public class AccountId implements Serializable {

    private String userLogin;

    private String accountCurrency;

    public AccountId(String userLogin, String accountCurrency) {
        this.userLogin = userLogin;
        this.accountCurrency = accountCurrency;
    }
}