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
    private  GetStockProfitService getStockProfitService;

    @Autowired
    public InterStockProfitService(RestTemplateBuilder restTemplateBuilder) {
        // TODO:
        this.restTemplate = restTemplateBuilder.build();
        final HttpHeaders headers = new HttpHeaders();
        this.httpEntity = new HttpEntity<>(headers);
    }

    public String GetmaxProfit(KakaoSkillRequest kakaoSkillRequest) throws JsonProcessingException {
        log.info("input: {}", kakaoSkillRequest);
        log.info("ticker: {}", kakaoSkillRequest.getUtterance());


        Profit profit=new Profit();

        //Ticker를 통한 API 호출
        String ticker=kakaoSkillRequest.getUtterance();
        log.info("추출한 ticker  {}", ticker);
        ResponseEntity<InterStockResponse[]> entity = GetInterStockInfoEntity(ticker);
        InterStockResponse[] arr = entity.getBody();
        //정상적으로데이터를 가져왔다면 이익 계산 메소드 실행
        //KakaoSkillResponse 생성
        KakaoSkillSimpleTextResponse response=new KakaoSkillSimpleTextResponse();

        if (arr != null) {
            // Profit 가져오는게 안됨.
           profit= getStockProfitService.getprofit(ticker,arr);


            response.setVersion("2.0");
            SimpleText simpleText=new SimpleText(profit.toString());
            Component outputs=new Component(simpleText);
            SkillTemplate template=new SkillTemplate(new ArrayList<Component>());
            template.getOutputs().add(outputs);
            response.setTemplate(template);
        } else {
            //에러 발생
        }

        ObjectMapper mapper = new ObjectMapper();
        String returnJson=mapper.writeValueAsString(response);
        log.info("response {}",returnJson);
        return returnJson;

    }


    public InterStockResponse[] TestGetmaxProfit(String ticker) throws JsonProcessingException {
        log.info("ticker: {}", ticker);
        //Ticker를 통한 API 호출
        ResponseEntity<InterStockResponse[]> entity = GetInterStockInfoEntity(ticker);
        InterStockResponse[] arr = entity.getBody();
        //정상적으로데이터를 가져왔다면 이익 계산 메소드 실행

        return arr;
    }

    public ResponseEntity<InterStockResponse[]> GetInterStockInfoEntity(String curTicker) {
        Date date = new Date();
        Calendar before180 = Calendar.getInstance();
        before180.add(Calendar.DATE, -180);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String enddate = format.format(date);
        String startdate = format.format(before180.getTime());
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
