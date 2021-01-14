package com.hasi.GetStockProfit.Presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hasi.GetStockProfit.Application.GetStockProfitService;
import com.hasi.GetStockProfit.Application.InterStockProfitService;
import com.hasi.GetStockProfit.Domain.Response.InterStockResponse;
import com.hasi.GetStockProfit.Domain.Request.KakaoSkillRequest;
import com.hasi.GetStockProfit.Domain.Response.KakaoSkillSimpleText.Component;
import com.hasi.GetStockProfit.Domain.Response.KakaoSkillSimpleText.KakaoSkillSimpleTextResponse;
import com.hasi.GetStockProfit.Domain.Response.KakaoSkillSimpleText.SimpleText;
import com.hasi.GetStockProfit.Domain.Response.KakaoSkillSimpleText.SkillTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class GetStockProfitController {

    @Autowired
    private InterStockProfitService interService;
    @Autowired
    private GetStockProfitService GSPService;


    // TODO : 국내 주식 코드를 받아서 정보를 조회해온다.

    // TODO : 해외 주식 코드를 받아서 정보를 조회해온다.
    @PostMapping(path = "/Inter/MakeInterStockProfit")
    public String MakeInterStockProfit(@RequestBody KakaoSkillRequest kakaoSkillRequest) throws Exception{
        return  GSPService.InterStockService(kakaoSkillRequest);
    }

    // TODO : TEST 컨트롤러- 결과를 JSON으로 출력해준다.
    @GetMapping(path = "/test/GetInterStockInfo", produces = "text/plain;charset=UTF-8")
    public String GetInterStockInfo(@RequestParam String ticker) throws Exception{
        ResponseEntity<InterStockResponse[]> entity= interService.GetInterStockInfoEntity(ticker);
        InterStockResponse[] list=entity.getBody();
        ObjectMapper mapper=new ObjectMapper();

        return  mapper.writeValueAsString(list);
    }

    @GetMapping(path = "/test/test", produces = "text/plain;charset=UTF-8")
    public String Test() throws Exception{
        return  "hello";
    }

}
