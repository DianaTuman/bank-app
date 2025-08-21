package com.dianatuman.practicum.bank.service;

import com.dianatuman.practicum.bank.dto.CurrencyDTO;
import com.dianatuman.practicum.bank.dto.RatesDTO;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Service
@RefreshScope
public class ExchangeService {

    private final RestTemplate restTemplate;
    private final MeterRegistry meterRegistry;

    public ExchangeService(RestTemplate restTemplate, MeterRegistry meterRegistry) {
        this.restTemplate = restTemplate;
        this.meterRegistry = meterRegistry;
    }

    public List<CurrencyDTO> getRates() {
        RatesDTO ratesDTO = null;
        try {
            ratesDTO = restTemplate.getForObject("/exchange/rates", RatesDTO.class);
        } catch (Throwable e) {
            log.error(e.getMessage());
            meterRegistry.counter("failed_get_rates").increment();
        }
        return ratesDTO.getCurrencyDTOS();
    }
}
