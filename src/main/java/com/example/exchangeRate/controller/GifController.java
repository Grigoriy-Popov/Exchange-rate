package com.example.exchangeRate.controller;

import com.example.exchangeRate.service.GifService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("rate-change")
@Slf4j
@RequiredArgsConstructor
public class GifController {
    private final GifService gifService;

    @GetMapping(produces = MediaType.IMAGE_GIF_VALUE)
    public byte[] getRateChangeAsGifByCurrencyCode(@RequestParam String currencyCode) throws IOException {
        return gifService.getGifByCurrencyCode(currencyCode);
    }
}
