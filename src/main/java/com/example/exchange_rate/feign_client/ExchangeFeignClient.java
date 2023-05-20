package com.example.exchange_rate.feign_client;

import com.example.exchange_rate.model.ExchangeRate;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "exchangeClient", url = "${app.base_currency_api_url}")
public interface ExchangeFeignClient {
    @GetMapping("/latest?base=${app.base_currency}&symbols={currencyCode}")
    ExchangeRate getTodayRate(@RequestParam String currencyCode);

    @GetMapping("/{yesterday}?base=${app.base_currency}&symbols={currencyCode}")
    ExchangeRate getYesterdayRate(@PathVariable String yesterday, @RequestParam String currencyCode);
}
