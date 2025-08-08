package com.dianatuman.practicum.exchange.service;

import com.dianatuman.practicum.exchange.dto.CurrencyDTO;
import com.dianatuman.practicum.exchange.dto.CurrencyTransferDTO;
import com.dianatuman.practicum.exchange.dto.RatesDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ExchangeService {

    private final RatesDTO currentRates = new RatesDTO();

    public RatesDTO getRates() {
        return currentRates;
    }

    public Float exchange(CurrencyTransferDTO currencyTransferDTO) {
        CurrencyDTO fromCurrency = currentRates.getRate(currencyTransferDTO.getFromCurrency())
                .orElseThrow();
        CurrencyDTO toCurrency = currentRates.getRate(currencyTransferDTO.getToCurrency())
                .orElseThrow();
        Float cashSumInRub = currencyTransferDTO.getAmount() / fromCurrency.getValue();
        return cashSumInRub * toCurrency.getValue();
    }

    @KafkaListener(topics = "${spring.kafka.topics}")
    public void listen(RatesDTO data, Acknowledgment ack, @Header(KafkaHeaders.OFFSET) long offset) {
        ack.acknowledge();
        log.info("Offset: {}", offset);
        log.info("Получены данные из топика: {}", data);
        currentRates.setCurrencyDTOS(data.getCurrencyDTOS());
    }
}
