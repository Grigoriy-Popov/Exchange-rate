package com.example.exchangeRate.service;

import java.io.IOException;

public interface CurrencyService {
    byte[] getGifByCurrencyCode(String currencyCode) throws IOException;
}
