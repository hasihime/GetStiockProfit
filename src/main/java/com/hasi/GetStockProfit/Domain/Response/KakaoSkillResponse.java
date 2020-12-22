package com.hasi.GetStockProfit.Domain.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KakaoSkillResponse {

    //https://vmpo.tistory.com/94 참고
    String version ="2.0";
    SkillTemplate template;
    ContextControl context;
    Map<String, String> data;

    public void makeData(Profit profit){
        data=new HashMap<String, String>();
        data.put("ticker",profit.getTicker());
        data.put("lowdate",profit.getLowdate());
        data.put("highdate",profit.getHighdate());
        data.put("maxprofit",Float.toString(profit.getMaxprofit()));
        data.put("maxprofit",profit.getEtc());
    }

    class SkillTemplate {
        List<Component> outputs;

        class Component {
            SimpleText simpleText;

            class SimpleText {
                String text;
            }
        }
    }

    class ContextControl {
        List<ContextValue> values;

        class ContextValue {
            String name;
            int lifeSpan;
            Map<String, String> params;
        }
    }


}
