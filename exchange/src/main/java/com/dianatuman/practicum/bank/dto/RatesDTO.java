package com.dianatuman.practicum.bank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatesDTO {

    List<CurrencyDTO> currencyDTOS;

    public Optional<CurrencyDTO> getRate(String name) {
        return currencyDTOS.stream().filter(currencyDTO ->
                currencyDTO.name.equals(name)).findFirst();
    }

}
