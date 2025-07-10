package com.dianatuman.practicum.accounts.dto;

import lombok.Data;

@Data
public class TransferDTO {

    AccountDTO fromAccountDTO;

    AccountDTO toAccountDTO;

    Float cashSum;
}
