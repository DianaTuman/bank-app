package com.dianatuman.practicum.cash.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CashDTO {

    AccountDTO accountDTO;

    Float cashSum;
}
