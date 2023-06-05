package com.example.exchange_rate.service;

import com.example.exchange_rate.exception.NotFoundException;
import com.example.exchange_rate.feign_client.ExchangeFeignClient;
import com.example.exchange_rate.model.CurrencyStatus;
import com.example.exchange_rate.model.ExchangeRate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Service("FeignClient")
@Slf4j
@RequiredArgsConstructor
public class FeignCurrencyServiceImpl implements CurrencyService {
    private final ExchangeFeignClient exchangeFeignClient;

    @Override
    public CurrencyStatus getCurrencyStatusByYesterday(String currencyCode) {
        BigDecimal todayRate = getLatestRate(currencyCode);
        BigDecimal yesterdayRate = getYesterdayRate(currencyCode);
        if (todayRate.compareTo(yesterdayRate) > 0) {
            log.info("Today rate go up");
            return CurrencyStatus.GO_UP;
        } else if (todayRate.compareTo(yesterdayRate) < 0) {
            log.info("Today rate go down");
            return CurrencyStatus.GO_DOWN;
        }
        log.info("Rate don't change");
        return CurrencyStatus.NO_CHANGE;
    }

    @Override
    public BigDecimal getLatestRate(String currencyCode) {
        ExchangeRate exchangeRate = exchangeFeignClient.getTodayRate(currencyCode);
        Map<String, BigDecimal> rates = exchangeRate.getRates();
        if (!rates.containsKey(currencyCode)) {
            throw new NotFoundException("Currency with code " + currencyCode + " not found");
        }
        BigDecimal latestRate = rates.get(currencyCode);
        log.info("Latest rate - {}", latestRate);
        return latestRate;
    }

    @Override
    public BigDecimal getYesterdayRate(String currencyCode) {
        String yesterday = LocalDate.now().minusDays(1).toString();
        ExchangeRate exchangeRate = exchangeFeignClient.getYesterdayRate(yesterday, currencyCode);
        Map<String, BigDecimal> rates = exchangeRate.getRates();
        if (!rates.containsKey(currencyCode)) {
            throw new NotFoundException("Currency with code " + currencyCode + " not found");
        }
        BigDecimal yesterdayRate = rates.get(currencyCode);
        log.info("Yesterday rate - {}", yesterdayRate);
        return yesterdayRate;
    }
}
