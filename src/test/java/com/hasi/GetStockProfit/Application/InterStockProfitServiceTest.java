package com.hasi.GetStockProfit.Application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hasi.GetStockProfit.Domain.Response.InterStockResponse;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@AutoConfigureMockRestServiceServer
@SpringBootTest
class InterStockProfitServiceTest {


    @Autowired
    private InterStockProfitService interStockProfitService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockRestServiceServer;

    @Test
    @DisplayName("올바른 ticker가 들어온 경우 정보를 가져옴")
    public void GetStockInfo_WhengivenCorrectTicker() throws JsonProcessingException {
        mockRestServiceServer = MockRestServiceServer.createServer(restTemplate);

        String ticker = "CorrectTicker";
        InterStockResponse stockResponse = new InterStockResponse(ticker, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        Date date = new Date();
        Calendar before180 = Calendar.getInstance();
        before180.add(Calendar.DATE, -180);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String enddate = format.format(date);
        String startdate = format.format(before180.getTime());
        String token = "de6162a413844946ee8c3535879d862ad97187fe";
        String url = String.format("https://api.tiingo.com/tiingo/daily/" + ticker + "/prices?startDate=%s&endDate=%s&token=%s",
                startdate, enddate, token);
        mockRestServiceServer.expect(requestTo(url))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(objectMapper.writeValueAsString(stockResponse)));

        //when
        ResponseEntity<InterStockResponse> responseEntity = interStockProfitService.GetInterStockInfoEntity(ticker);

        //then
        mockRestServiceServer.verify();
        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals(stockResponse, responseEntity.getBody());
    }

}