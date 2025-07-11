package com.dianatuman.practicum.transfer.dto;

import lombok.Data;

@Data
public class TransferDTO {

    AccountDTO fromAccountDTO;

    AccountDTO toAccountDTO;

    Float cashSum;
}
