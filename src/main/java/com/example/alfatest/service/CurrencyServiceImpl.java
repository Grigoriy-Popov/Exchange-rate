package com.example.alfatest.service;

import com.example.alfatest.model.CurrencyStatus;
import com.example.alfatest.model.ExchangeRate;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;

@Service
@Slf4j
public class CurrencyServiceImpl implements CurrencyService {
    private final HttpClient client;
    ObjectMapper mapper;

    public CurrencyServiceImpl(ObjectMapper mapper) {
        client = HttpClient.newHttpClient();
        this.mapper = mapper;
    }

    @Override
    public void getGifByCurrencyCode(String currencyCode) {

    }

    @Override
    public CurrencyStatus isCurrencyIncrease(String currencyCode) {
        double todayRate = getTodayRate(currencyCode);
        double yesterdayRate = getYesterdayRate(currencyCode);
        if (todayRate > yesterdayRate) {
            return CurrencyStatus.INCREASED;
        } else if (todayRate < yesterdayRate) {
            return CurrencyStatus.GO_DOWN;
        }
        return CurrencyStatus.INCREASED;
    }

    private double getTodayRate(String currencyCode) {
        URI url = URI.create("https://api.exchangerate.host/latest?base=USD&symbols=" + currencyCode);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .build();

        double rate = 0.0;
        try {
            final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                ExchangeRate exchangeRate = mapper.readValue(response.body(), ExchangeRate.class);
                rate = exchangeRate.getRates().get("RUB");
            } else {
                log.debug("Что-то пошло не так. Сервер вернул код состояния: " + response.statusCode());
            }
        } catch (NullPointerException | IOException | InterruptedException e) {
            System.out.println("Во время выполнения запроса возникла ошибка.\n" +
                    "Проверьте, пожалуйста, адрес и повторите попытку.");
        }
        System.out.println(rate);
        return rate;
    }

    private double getYesterdayRate(String currencyCode) {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        URI url = URI.create("https://api.exchangerate.host/" + yesterday + "?base=USD&symbols=" + currencyCode);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .GET()
                .build();

        double rate = 0.0;
        try {
            final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                ExchangeRate exchangeRate = mapper.readValue(response.body(), ExchangeRate.class);
                rate = exchangeRate.getRates().get("RUB");
            } else {
                System.out.println("Что-то пошло не так. Сервер вернул код состояния: " + response.statusCode());
            }
        } catch (NullPointerException | IOException | InterruptedException e) {
            System.out.println("Во время выполнения запроса возникла ошибка.\n" +
                    "Проверьте, пожалуйста, адрес и повторите попытку.");
        }
        System.out.println(rate);
        return rate;
    }
}
