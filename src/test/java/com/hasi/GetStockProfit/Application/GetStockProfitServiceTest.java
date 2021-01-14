package com.hasi.GetStockProfit.Application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hasi.GetStockProfit.Domain.Request.KakaoSkillRequest;
import com.hasi.GetStockProfit.Domain.Response.KakaoSkillSimpleText.Component;
import com.hasi.GetStockProfit.Domain.Response.KakaoSkillSimpleText.KakaoSkillSimpleTextResponse;
import com.hasi.GetStockProfit.Domain.Response.KakaoSkillSimpleText.SimpleText;
import com.hasi.GetStockProfit.Domain.Response.KakaoSkillSimpleText.SkillTemplate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;


@SpringBootTest
class GetStockProfitServiceTest {

    @Autowired
    private GetStockProfitService getStockProfitService;


    @Test
    @DisplayName("kakaoSkill 입력값이 옳은 경우")
    public void CorrectSkill() {
        KakaoSkillRequest skillRequest = new KakaoSkillRequest();
        skillRequest.makeUserRequest();
        skillRequest.makeUtterance("correct input");

        //when
        boolean actual = getStockProfitService.CheckSkillRequest(skillRequest);

        //then
        Assertions.assertTrue(actual);
        Assertions.assertEquals(skillRequest.getUtterance(), "correct input");
    }

    @Test
    @DisplayName("kakaoSkill 입력값이 없는 경우")
    public void NullSkill() {
        KakaoSkillRequest skillRequest = new KakaoSkillRequest();
        skillRequest.makeUserRequest();

        //when
        boolean actual = getStockProfitService.CheckSkillRequest(skillRequest);

        //then
        Assertions.assertFalse(actual);
        Assertions.assertNull(skillRequest.getUtterance());
    }

    @Test
    @DisplayName("kakaoSkill Response 전송")
    public void SendSkillResponse() throws JsonProcessingException {
        KakaoSkillSimpleTextResponse expected = new KakaoSkillSimpleTextResponse();
        SimpleText ExpectedSimpleText = new SimpleText("Expected");
        expected.setVersion("2.0");
        Component outputs = new Component(ExpectedSimpleText);
        SkillTemplate template = new SkillTemplate(new ArrayList<Component>());
        template.getOutputs().add(outputs);
        expected.setTemplate(template);

        ObjectMapper mapper = new ObjectMapper();
        String expectedJson = mapper.writeValueAsString(expected);

        //when
        KakaoSkillSimpleTextResponse skillResponse = new KakaoSkillSimpleTextResponse();
        SimpleText simpleText = new SimpleText("Expected");

        String actualJson = getStockProfitService.MakeKakaoResponse(skillResponse, simpleText);

        //then
        Assertions.assertEquals(expectedJson, actualJson);
    }

}