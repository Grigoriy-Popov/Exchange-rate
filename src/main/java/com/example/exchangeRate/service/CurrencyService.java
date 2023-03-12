package com.example.exchangeRate.service;

import com.example.exchangeRate.model.CurrencyStatus;

public interface CurrencyService {

    double getLatestRate(String currencyCode);

    double getYesterdayRate(String currencyCode);

    CurrencyStatus getCurrencyStatusByYesterday(String currencyCode);
}
