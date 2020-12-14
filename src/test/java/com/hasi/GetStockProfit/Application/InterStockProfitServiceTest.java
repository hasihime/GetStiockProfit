package com.hasi.GetStockProfit.Application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hasi.GetStockProfit.Domain.Response.InterStockResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;


@RestClientTest(value = InterStockProfitService.class)
class InterStockProfitServiceTest {
    @Autowired
    private InterStockProfitService interStockProfitService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MockRestServiceServer mockRestServiceServer;


    @Test
    @DisplayName("올바른 ticker가 들어온 경우 정보를 가져옴")
    public void GetStockInfo_When_givenCorrectTicker() throws JsonProcessingException {

        String ticker = "CorrectTicker";
        InterStockResponse[] expected ={new InterStockResponse(ticker, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)};

        Date date = new Date();
        Calendar before180 = Calendar.getInstance();
        before180.add(Calendar.DATE, -180);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String enddate = format.format(date);
        String startdate = format.format(before180.getTime());
        String token = "de6162a413844946ee8c3535879d862ad97187fe";
        String url = String.format("https://api.tiingo.com/tiingo/daily/%s/prices?startDate=%s&endDate=%s&token=%s",
                ticker,startdate, enddate, token);

        mockRestServiceServer.expect(requestTo(url))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString(expected)));

        //when
        ResponseEntity<InterStockResponse[]> actual = interStockProfitService.GetInterStockInfoEntity(ticker);

        //then
        mockRestServiceServer.verify();
        Assertions.assertTrue(actual.getStatusCode().is2xxSuccessful());
        Assertions.assertArrayEquals(expected, actual.getBody());
    }

    @Test
    @DisplayName("잘못된 ticker가 들어온 경우 잘못왔다고 리턴함")
    public void GetStockInfo_When_givenWrongicker() throws JsonProcessingException {

        String ticker = "WrongTicker";
        InterStockResponse[] expected = null;

        Date date = new Date();
        Calendar before180 = Calendar.getInstance();
        before180.add(Calendar.DATE, -180);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String enddate = format.format(date);
        String startdate = format.format(before180.getTime());
        String token = "de6162a413844946ee8c3535879d862ad97187fe";
        String url = String.format("https://api.tiingo.com/tiingo/daily/%s/prices?startDate=%s&endDate=%s&token=%s",
                ticker,startdate, enddate, token);

        mockRestServiceServer.expect(requestTo(url))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.NOT_FOUND)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString(expected)));

        //when
        ResponseEntity<InterStockResponse[]> actual = interStockProfitService.GetInterStockInfoEntity(ticker);

        //then
        mockRestServiceServer.verify();
        Assertions.assertEquals(expected, actual.getBody());
    }
}