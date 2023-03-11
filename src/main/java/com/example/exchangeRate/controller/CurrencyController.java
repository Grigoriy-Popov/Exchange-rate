package com.example.exchangeRate.controller;

import com.example.exchangeRate.service.CurrencyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping
@Slf4j
public class CurrencyController {
    private final CurrencyService currencyService;

    public CurrencyController(@Qualifier("FeignClient") CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping(produces = MediaType.IMAGE_GIF_VALUE)
    public byte[] getGifByCurrencyCode(@RequestParam String currencyCode) throws IOException {
        return currencyService.getGifByCurrencyCode(currencyCode);
    }
}
