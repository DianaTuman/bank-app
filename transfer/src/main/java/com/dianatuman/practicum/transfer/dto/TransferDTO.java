package com.dianatuman.practicum.transfer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferDTO {

    AccountDTO fromAccountDTO;

    AccountDTO toAccountDTO;

    Float amountFrom;

    Float amountTo;

    public String formMessage() {
        return "Money was transferred from user " + fromAccountDTO.getUserLogin() +
                " account " + fromAccountDTO.getAccountCurrency() +
                " to user " + toAccountDTO.getUserLogin() +
                " account " + toAccountDTO.getAccountCurrency();
    }
}
