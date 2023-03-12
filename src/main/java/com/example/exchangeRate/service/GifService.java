package com.example.exchangeRate.service;

import java.io.IOException;

public interface GifService {

    byte[] getGifByCurrencyCode(String currencyCode) throws IOException;

}
