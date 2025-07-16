package com.dianatuman.practicum.exchange.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatesDTO {

    List<CurrencyDTO> currencyDTOS;

}
