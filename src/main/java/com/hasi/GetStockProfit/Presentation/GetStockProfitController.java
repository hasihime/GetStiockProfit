package com.hasi.GetStockProfit.Presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hasi.GetStockProfit.Application.InterStockProfitService;
import com.hasi.GetStockProfit.Domain.Response.InterStockResponse;
import com.hasi.GetStockProfit.Domain.Request.KakaoSkillRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class GetStockProfitController {

    @Autowired
    private InterStockProfitService interService;


    // TODO : 카카오톡 채널봇에서 국내주식인지 해외주식인지 받아서 들어온 요청에 따라 분기 제공


    // TODO : 국내 주식 코드를 받아서 정보를 조회해온다.

    // TODO : 해외 주식 코드를 받아서 정보를 조회해온다.
    @PostMapping(path = "/Inter/GetInterStockInfo")
    public String GetInterStockProfit(@RequestBody KakaoSkillRequest kakaoSkillRequest) throws Exception{
        return  interService.GetmaxProfit(kakaoSkillRequest);
    }

    // TODO : TEST 컨트롤러- 결과를 JSON으로 출력해준다.
    @GetMapping(path = "/test/GetInterStockInfo", produces = "text/plain;charset=UTF-8")
    public String GetInterStockInfo(@RequestParam String ticker) throws Exception{
        ResponseEntity<InterStockResponse[]> entity= interService.GetInterStockInfoEntity(ticker);
        InterStockResponse[] list=entity.getBody();
        ObjectMapper mapper=new ObjectMapper();

        return  mapper.writeValueAsString(list);
    }

}
