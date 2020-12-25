package com.hasi.GetStockProfit.Domain.Response.KakaoSkillSimpleText;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KakaoSkillSimpleTextResponse {

    //https://vmpo.tistory.com/94 참고
    String version ;
    SkillTemplate template;
//    ContextControl context;
//    Map<String, String> data;



//    public void makeData(Profit profit){
//        data=new HashMap<String, String>();
//        data.put("ticker",profit.getTicker());
//        data.put("lowdate",profit.getLowdate());
//        data.put("highdate",profit.getHighdate());
//        data.put("maxprofit",Float.toString(profit.getMaxprofit()));
//        data.put("maxprofit",profit.getEtc());
//    }




//    @Data
//    @AllArgsConstructor
//    @NoArgsConstructor
//    class ContextControl {
//        List<ContextValue> values;
//
//        class ContextValue {
//            String name;
//            int lifeSpan;
//            Map<String, String> params;
//        }
//    }


}
