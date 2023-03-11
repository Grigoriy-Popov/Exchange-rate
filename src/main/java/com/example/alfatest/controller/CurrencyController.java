package com.example.alfatest.controller;

import com.example.alfatest.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
@Slf4j
public class CurrencyController {
    private final CurrencyService currencyService;

    @GetMapping
    public void getGifByCurrencyCode(@RequestParam String currencyCode) {
//        currencyService.getGifByCurrencyCode("RUB");
        currencyService.isCurrencyIncrease(currencyCode);
    }
}
