package com.example.exchangeRate.service;

import com.example.exchangeRate.feignClient.ExchangeFeignClient;
import com.example.exchangeRate.model.CurrencyStatus;
import com.example.exchangeRate.model.ExchangeRate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service("FeignClient")
@Slf4j
@RequiredArgsConstructor
public class FeignCurrencyServiceImpl implements CurrencyService {
    private final ExchangeFeignClient exchangeFeignClient;

    @Override
    public CurrencyStatus getCurrencyStatusByYesterday(String currencyCode) {
        double todayRate = getLatestRate(currencyCode);
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

    @Override
    public double getLatestRate(String currencyCode) {
        ExchangeRate exchangeRate = exchangeFeignClient.getTodayRate(currencyCode);
        System.out.println(exchangeRate.getRates().get(currencyCode));
        return exchangeRate.getRates().get(currencyCode);
    }

    @Override
    public double getYesterdayRate(String currencyCode) {
        String yesterday = LocalDate.now().minusDays(1).toString();
        ExchangeRate exchangeRate = exchangeFeignClient.getYesterdayRate(yesterday, currencyCode);
        System.out.println(exchangeRate.getRates().get(currencyCode));
        return exchangeRate.getRates().get(currencyCode);
    }
}
