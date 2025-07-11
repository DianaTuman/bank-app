package com.dianatuman.practicum.bank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CashDTO {

    AccountDTO accountDTO;

    Float cashSum;

    public CashDTO(AccountDTO accountDTO) {
        this.accountDTO = accountDTO;
    }
}
