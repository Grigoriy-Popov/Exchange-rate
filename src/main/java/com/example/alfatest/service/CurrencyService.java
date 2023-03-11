package com.example.alfatest.service;

import com.example.alfatest.model.CurrencyStatus;

public interface CurrencyService {
    void getGifByCurrencyCode(String currencyCode);

    CurrencyStatus isCurrencyIncrease(String currencyCode);
}
