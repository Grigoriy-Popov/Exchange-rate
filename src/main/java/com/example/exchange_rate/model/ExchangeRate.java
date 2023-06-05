package com.example.exchange_rate.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Data
public class ExchangeRate {
    private String base;
    private LocalDate date;
    private Map<String, BigDecimal> rates;
}
