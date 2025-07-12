package com.dianatuman.practicum.accounts.dto;

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
}
