package com.example.exchangeRate.feign_client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "choiceMarketClient", url = "${choice-market.base_gif_api_url}")
public interface ChoiceMarketFeignClient {
    @GetMapping
    byte[] getNoChangeGif();
}
