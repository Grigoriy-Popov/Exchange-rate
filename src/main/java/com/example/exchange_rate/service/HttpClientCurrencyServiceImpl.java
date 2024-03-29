package com.example.exchange_rate.service;

import com.example.exchange_rate.model.CurrencyStatus;
import com.example.exchange_rate.model.ExchangeRate;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;

@Service("HttpClient")
@Slf4j
public class HttpClientCurrencyServiceImpl implements CurrencyService {
    private final HttpClient client;
    private final ObjectMapper mapper;
    private final String baseCurrency;
    private final String baseCurrencyApiURL;

    public HttpClientCurrencyServiceImpl(ObjectMapper mapper,
                                         @Value("${app.base_currency}") String baseCurrency,
                                         @Value("${app.base_currency_api_url}") String baseCurrencyApiURL) {
        client = HttpClient.newHttpClient();
        this.mapper = mapper;
        this.baseCurrency = baseCurrency;
        this.baseCurrencyApiURL = baseCurrencyApiURL;
    }

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
        URI url = URI.create(baseCurrencyApiURL + "/latest?base=" + baseCurrency + "&symbols=" + currencyCode);
        return sendRequestToGetRate(url, currencyCode);
    }

    @Override
    public BigDecimal getYesterdayRate(String currencyCode) {
        var yesterday = LocalDate.now().minusDays(1);
        URI url = URI.create(String.format("%s/%s?base=%s&symbols=%s",
                baseCurrencyApiURL, yesterday, baseCurrency, currencyCode));
        return sendRequestToGetRate(url, currencyCode);
    }

    private BigDecimal sendRequestToGetRate(URI url, String currencyCode) {
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();

        BigDecimal rate = new BigDecimal(0);
        try {
            final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                ExchangeRate exchangeRate = mapper.readValue(response.body(), ExchangeRate.class);
                rate = exchangeRate.rates().get(currencyCode);
            } else {
                log.debug("Что-то пошло не так. Сервер вернул код состояния: " + response.statusCode());
            }
        } catch (NullPointerException | IOException | InterruptedException e) {
            System.out.println("Во время выполнения запроса возникла ошибка.\n" +
                    "Проверьте, пожалуйста, адрес и повторите попытку.");
            log.debug("Во время выполнения запроса возникла ошибка.");
        }
        System.out.println(rate);
        return rate;
    }
}
