package com.dianatuman.practicum.accounts.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
public class AccountDTO {

    private String userLogin;

    private String accountCurrency;

    private Float value;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AccountDTO that = (AccountDTO) o;
        return Objects.equals(userLogin, that.userLogin) && Objects.equals(accountCurrency, that.accountCurrency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userLogin, accountCurrency);
    }
}
