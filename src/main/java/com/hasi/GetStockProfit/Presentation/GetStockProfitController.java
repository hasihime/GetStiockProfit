package com.hasi.GetStockProfit.Presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
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


    // TODO : 카카오톡 채널봇에서 국내주식인지 해외주식인지 받아서 들어온 요청에 따라 분기 제공


    // TODO : 국내 주식 코드를 받아서 정보를 조회해온다.

    // TODO : 해외 주식 코드를 받아서 정보를 조회해온다.
    @PostMapping(path = "/Inter/GetInterStockInfo")
    public String GetInterStockProfit(@RequestBody KakaoSkillRequest kakaoSkillRequest) throws Exception{
//        KakaoSkillSimpleTextResponse response=new KakaoSkillSimpleTextResponse();
//        response.setVersion("2.0");
//        SimpleText simpleText = new SimpleText("잠시만 기다려주세요.");
//        Component outputs = new Component(simpleText);
//        SkillTemplate template = new SkillTemplate(new ArrayList<Component>());
//        template.getOutputs().add(outputs);
//        response.setTemplate(template);

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

    @GetMapping(path = "/test/test", produces = "text/plain;charset=UTF-8")
    public String Test() throws Exception{
        return  "hello";
    }

    @GetMapping(path = "/test/test1", produces = "text/plain;charset=UTF-8")
    public String Test1() throws Exception{
        return  "hello1";
    }
}
