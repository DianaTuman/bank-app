package com.dianatuman.practicum.notifications.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CashDTO {

    AccountDTO accountDTO;

    Float cashSum;

    public String formMessage() {
        if (cashSum > 0) {
            return accountDTO.getAccountCurrency() + " account for user " + accountDTO.getUserLogin()
                    + " received payment " + cashSum;
        } else {
            return accountDTO.getAccountCurrency() + " account for user " + accountDTO.getUserLogin()
                    + " cashed  " + cashSum;
        }

    }
}
