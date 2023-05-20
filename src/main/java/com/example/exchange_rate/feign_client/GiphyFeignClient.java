package com.example.exchange_rate.feign_client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "giphyClient", url = "${giphy.base_gif_api_url}")
public interface GiphyFeignClient {
    @GetMapping("?api_key=${giphy.key}&tag={tag}&rating=g")
    Map getGifInformationByKeyword(@PathVariable("tag") String keyword);
}
