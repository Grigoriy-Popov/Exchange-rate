package com.example.exchange_rate.service;

import java.io.IOException;

public interface GifService {

    byte[] getGifByCurrencyCode(String currencyCode) throws IOException;

}
