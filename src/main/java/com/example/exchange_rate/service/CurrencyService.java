package com.example.exchange_rate.service;

import com.example.exchange_rate.model.CurrencyStatus;

import java.math.BigDecimal;

public interface CurrencyService {

    BigDecimal getLatestRate(String currencyCode);

    BigDecimal getYesterdayRate(String currencyCode);

    CurrencyStatus getCurrencyStatusByYesterday(String currencyCode);
}
