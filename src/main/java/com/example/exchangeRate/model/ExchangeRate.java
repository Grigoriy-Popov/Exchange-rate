package com.example.exchangeRate.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.Map;

@Data
public class ExchangeRate {
    private String base;
    private LocalDate date;
    private Map<String, Double> rates;
}
