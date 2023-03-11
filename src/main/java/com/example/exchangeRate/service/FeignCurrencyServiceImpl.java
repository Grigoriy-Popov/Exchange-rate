package com.example.exchangeRate.service;

import com.example.exchangeRate.feignClient.ExchangeFeignClient;
import com.example.exchangeRate.model.CurrencyStatus;
import com.example.exchangeRate.model.ExchangeRate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Slf4j
@RequiredArgsConstructor
public class FeignCurrencyServiceImpl implements CurrencyService {
    private final ExchangeFeignClient exchanger;

    @Override
    public void getGifByCurrencyCode(String currencyCode) {
        CurrencyStatus status = getCurrencyStatusByYesterday(currencyCode);
        switch (status) {
            case GO_UP:
                break;
            case GO_DOWN:
                break;
            case NO_CHANGE:
                break;
            default:
                throw new RuntimeException("Ошибка");
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
}
