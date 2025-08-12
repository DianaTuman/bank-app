package com.dianatuman.practicum.generator.service;

import com.dianatuman.practicum.generator.dto.CurrencyDTO;
import com.dianatuman.practicum.generator.dto.RatesDTO;
import com.dianatuman.practicum.generator.enums.BankCurrency;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
public class ExchangeGeneratorService {

    private final KafkaTemplate<String, RatesDTO> kafkaTemplate;

    @Value("${bank_app_rates_topic}")
    private String ratesTopic;

    public ExchangeGeneratorService(KafkaTemplate<String, RatesDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Scheduled(cron = "* * * * * *")
    public void generateExchange() throws ExecutionException, InterruptedException {
        List<CurrencyDTO> currencyDTOS = new ArrayList<>();
        Random rand = new Random();
        currencyDTOS.add(new CurrencyDTO(BankCurrency.RUB, 1.0F));
        currencyDTOS.add(new CurrencyDTO(BankCurrency.USD, rand.nextFloat(0.01F, 0.02F)));
        currencyDTOS.add(new CurrencyDTO(BankCurrency.CNY, rand.nextFloat(0.08F, 0.1F)));

        kafkaTemplate.send(ratesTopic, new RatesDTO(currencyDTOS))
                .whenComplete((result, e) -> {
                    if (e != null) {
                        log.error("Ошибка при отправке сообщения: {}", e.getMessage(), e);
                        return;
                    }

                    RecordMetadata metadata = result.getRecordMetadata();
                    log.info("Сообщение отправлено. Topic = {}, partition = {}, offset = {}",
                            metadata.topic(), metadata.partition(), metadata.offset());
                }).get();
    }
}
