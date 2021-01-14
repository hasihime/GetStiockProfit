package com.hasi.GetStockProfit.Application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hasi.GetStockProfit.Domain.Request.KakaoSkillRequest;
import com.hasi.GetStockProfit.Domain.Response.InterStockResponse;
import com.hasi.GetStockProfit.Domain.Response.KakaoSkillSimpleText.Component;
import com.hasi.GetStockProfit.Domain.Response.KakaoSkillSimpleText.KakaoSkillSimpleTextResponse;
import com.hasi.GetStockProfit.Domain.Response.KakaoSkillSimpleText.SimpleText;
import com.hasi.GetStockProfit.Domain.Response.KakaoSkillSimpleText.SkillTemplate;
import com.hasi.GetStockProfit.Domain.Response.Profit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetStockProfitService {

    @Autowired
    private CalculationProfitService calculationProfitService;
    @Autowired
    private InterStockProfitService interStockProfitService;

    public String InterStockService(KakaoSkillRequest kakaoSkillRequest) throws JsonProcessingException {
        log.info("input: {}", kakaoSkillRequest);
        log.info("ticker: {}", kakaoSkillRequest.getUtterance());

        //KakaoSkillResponse 생성
        KakaoSkillSimpleTextResponse response = new KakaoSkillSimpleTextResponse();
        SimpleText simpleText;

        // TODO: 1.KaKao Skill Request 유효성 판단
        if (CheckSkillRequest(kakaoSkillRequest)) {
            String ticker = kakaoSkillRequest.getUtterance();

            // TODO: 2.InterService에서 주식 히스토리 가져옴
            ResponseEntity<InterStockResponse[]> entity = interStockProfitService.GetInterStockInfoEntity(ticker);

            if (entity.getBody() != null) {
                InterStockResponse[] arr = entity.getBody();

                // TODO: 3. CalculationProfitService에서 수익 계산함
                Profit profit = calculationProfitService.calculateProfit(ticker, arr);
                String text = profit.getTicker() + "는 " + profit.getLowdate()
                        + "에 구매해서"
                        + profit.getHighdate() + "에 판매하면"
                        + profit.getMaxprofit() + "$의 이득을 볼 수 있습니다.";
                simpleText = new SimpleText(text);
            } else {
                simpleText = new SimpleText("Ticker 입력값이 정확하지 않습니다. 확인해주세요");
            }
        } else {
            simpleText = new SimpleText("카카오 스킬 입력 오류. ");
        }
        String returnJson = MakeKakaoResponse(response, simpleText);
        log.info("response {}", returnJson);

        // TODO: 4. 결과 Json을 Return한다.
        return returnJson;
    }

    boolean CheckSkillRequest(KakaoSkillRequest kakaoSkillRequest) {
        if (kakaoSkillRequest.getUtterance() != null) {
            return true;
        } else return false;
    }

    String MakeKakaoResponse(KakaoSkillSimpleTextResponse response, SimpleText simpleText) throws JsonProcessingException {
        response.setVersion("2.0");
        Component outputs = new Component(simpleText);
        SkillTemplate template = new SkillTemplate(new ArrayList<Component>());
        template.getOutputs().add(outputs);
        response.setTemplate(template);

        ObjectMapper mapper = new ObjectMapper();
        String returnJson = mapper.writeValueAsString(response);
        return returnJson;
    }

}
