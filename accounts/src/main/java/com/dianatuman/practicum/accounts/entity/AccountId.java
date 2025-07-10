package com.dianatuman.practicum.accounts.entity;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@EqualsAndHashCode
public class AccountId implements Serializable {

    private User userLogin;

    private String accountCurrency;

    public AccountId(User userLogin, String accountCurrency) {
        this.userLogin = userLogin;
        this.accountCurrency = accountCurrency;
    }
}