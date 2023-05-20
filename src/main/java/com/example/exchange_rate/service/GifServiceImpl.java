package com.example.exchange_rate.service;

import com.example.exchange_rate.feign_client.ChoiceMarketFeignClient;
import com.example.exchange_rate.feign_client.GiphyFeignClient;
import com.example.exchange_rate.model.CurrencyStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class GifServiceImpl implements GifService {

    @Qualifier("FeignClient")
    private final CurrencyService currencyService;
    private final GiphyFeignClient gifFeignClient;
    private final ChoiceMarketFeignClient choiceMarketFeignClient;

    @Override
    public byte[] getGifByCurrencyCode(String currencyCode) throws IOException {
        CurrencyStatus status = currencyService.getCurrencyStatusByYesterday(currencyCode);
        String gifURL;
        switch (status) {
            case GO_UP -> gifURL = getGifURLByKeywordFromGiphy("rich");
            case GO_DOWN -> gifURL = getGifURLByKeywordFromGiphy("broke");
            case NO_CHANGE -> {
                return choiceMarketFeignClient.getNoChangeGif();
            }
            default -> throw new RuntimeException("Ошибка");
        }
        try (InputStream in = new URL(gifURL).openStream()) {
            return in.readAllBytes();
        }
    }

    private String getGifURLByKeywordFromGiphy(String keyword) {
        Map gifParameters = gifFeignClient.getGifInformationByKeyword(keyword);
        Map data = (HashMap) gifParameters.get("data");
        String urlGifId = String.valueOf((data.get("id")));
        return "https://i.giphy.com/media/" + urlGifId + "/giphy.gif";
    }
}
