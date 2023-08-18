package com.example.exchange_rate.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

public record ExchangeRate(String base, LocalDate date, Map<String, BigDecimal> rates) {
}
