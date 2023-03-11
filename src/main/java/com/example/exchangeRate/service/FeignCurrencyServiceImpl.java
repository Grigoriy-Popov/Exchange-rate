package com.example.exchangeRate.service;

import com.example.exchangeRate.feignClient.ExchangeFeignClient;
import com.example.exchangeRate.feignClient.GifFeignClient;
import com.example.exchangeRate.model.CurrencyStatus;
import com.example.exchangeRate.model.ExchangeRate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service("FeignClient")
@Slf4j
@RequiredArgsConstructor
public class FeignCurrencyServiceImpl implements CurrencyService {
    private final ExchangeFeignClient exchanger;
    private final GifFeignClient gifFeignClient;

    @Override
    public byte[] getGifByCurrencyCode(String currencyCode) throws IOException {
        CurrencyStatus status = getCurrencyStatusByYesterday(currencyCode);
        String gifURL;
        switch (status) {
            case GO_UP:
                gifURL = getGifURLByKeyword("rich");
                break;
            case GO_DOWN:
                gifURL = getGifURLByKeyword("broke");
                break;
            case NO_CHANGE:
                gifURL = getGifURLByKeyword("no change");
                break;
            default:
                throw new RuntimeException("Ошибка");
        }
        try (InputStream in = new URL(gifURL).openStream()) {
            return in.readAllBytes();
        }
    }

    private CurrencyStatus getCurrencyStatusByYesterday(String currencyCode) {
        double todayRate = getTodayRate(currencyCode);
        double yesterdayRate = getYesterdayRate(currencyCode);
        if (todayRate > yesterdayRate) {
            System.out.println(CurrencyStatus.GO_UP);
            return CurrencyStatus.GO_UP;
        } else if (todayRate < yesterdayRate) {
            System.out.println(CurrencyStatus.GO_DOWN);
            return CurrencyStatus.GO_DOWN;
        }
        System.out.println(CurrencyStatus.NO_CHANGE);
        return CurrencyStatus.NO_CHANGE;
    }

    private double getTodayRate(String currencyCode) {
        ExchangeRate exchangeRate = exchanger.getTodayRate(currencyCode);
        System.out.println(exchangeRate.getRates().get(currencyCode));
        return exchangeRate.getRates().get(currencyCode);
    }

    private double getYesterdayRate(String currencyCode) {
        String yesterday = LocalDate.now().minusDays(1).toString();
        ExchangeRate exchangeRate = exchanger.getYesterdayRate(yesterday, currencyCode);
        System.out.println(exchangeRate.getRates().get(currencyCode));
        return exchangeRate.getRates().get(currencyCode);
    }

    private String getGifURLByKeyword(String keyword) {
        Map gifParameters = gifFeignClient.getGifByKeyword(keyword);
        Map data = (HashMap) gifParameters.get("data");
        String urlGifId = String.valueOf((data.get("id")));
        return "https://i.giphy.com/media/" + urlGifId + "/giphy.gif";
    }
}
