package com.hasi.GetStockProfit.Application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hasi.GetStockProfit.Domain.Response.KakaoSkillSimpleText.Component;
import com.hasi.GetStockProfit.Domain.Response.KakaoSkillSimpleText.KakaoSkillSimpleTextResponse;
import com.hasi.GetStockProfit.Domain.Response.KakaoSkillSimpleText.SimpleText;
import com.hasi.GetStockProfit.Domain.Response.KakaoSkillSimpleText.SkillTemplate;
import com.hasi.GetStockProfit.Domain.Response.Profit;
import com.hasi.GetStockProfit.Domain.Response.InterStockResponse;
import com.hasi.GetStockProfit.Domain.Request.KakaoSkillRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class InterStockProfitService {

    private final RestTemplate restTemplate;
    private final HttpEntity<String> httpEntity;

    @Autowired
    public InterStockProfitService(RestTemplateBuilder restTemplateBuilder) {
        // TODO:
        this.restTemplate = restTemplateBuilder.build();
        final HttpHeaders headers = new HttpHeaders();
        this.httpEntity = new HttpEntity<>(headers);
    }


    public ResponseEntity<InterStockResponse[]> GetInterStockInfoEntity(String curTicker) {
        Date date = new Date();
        Calendar beforeYear = Calendar.getInstance();
        beforeYear.add(Calendar.DATE, -365);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String enddate = format.format(date);
        String startdate = format.format(beforeYear.getTime());
        String token = "de6162a413844946ee8c3535879d862ad97187fe";
        String url = String.format("https://api.tiingo.com/tiingo/daily/%s/prices?startDate=%s&endDate=%s&token=%s",
                curTicker, startdate, enddate, token);
        try {
            return restTemplate.exchange(url, HttpMethod.GET, httpEntity, InterStockResponse[].class, curTicker);
        } catch (HttpStatusCodeException exception) {
            log.info("No result about {}", curTicker);
            return ResponseEntity.status(exception.getStatusCode()).body(null);
        }
    }


}
