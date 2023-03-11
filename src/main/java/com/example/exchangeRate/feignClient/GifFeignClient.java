package com.example.exchangeRate.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "gif", url = "${app.base_gif_api_url}")
public interface GifFeignClient {
    @GetMapping("?api_key=${giphy.key}&tag={tag}&rating=g")
    Map getGifByKeyword(@PathVariable("tag") String keyword);
}
