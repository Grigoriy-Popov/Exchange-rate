package com.example.exchange_rate.service;

import com.example.exchange_rate.model.CurrencyStatus;

public interface CurrencyService {

    double getLatestRate(String currencyCode);

    double getYesterdayRate(String currencyCode);

    CurrencyStatus getCurrencyStatusByYesterday(String currencyCode);
}
