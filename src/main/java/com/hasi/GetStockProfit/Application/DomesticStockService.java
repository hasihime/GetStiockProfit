package com.hasi.GetStockProfit.Application;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class DomesticStockService {
    private final RestTemplate restTemplate;
    private final HttpEntity<String> httpEntity;

    @Autowired
    public DomesticStockService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
        final HttpHeaders headers = new HttpHeaders();
        this.httpEntity = new HttpEntity<>(headers);
    }

}
