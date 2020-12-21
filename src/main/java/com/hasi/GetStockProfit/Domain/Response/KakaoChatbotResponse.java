package com.hasi.GetStockProfit.Domain.Response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.apache.catalina.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KakaoChatbotResponse {
    @JsonProperty(value = "intent")
    Intent intent;
    @JsonProperty(value = "userRequest")
    UserRequest userRequest;

    @JsonProperty(value = "action")
    Action action;

    public String getTciker(){
        return action.params.ticker;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    class Intent{
        @JsonProperty(value = "id")
        String id;
        @JsonProperty(value = "name")
        String name;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    class UserRequest{
        @JsonProperty(value="timezone")
        String timezone;
        @JsonProperty(value="params")
        Params params;

        class Params{
            @JsonProperty(value = "ignoreMe")
            String ignoreMe;
        }
}
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    class Action{
        @JsonProperty(value = "name")
        String name;
        @JsonProperty(value = "clientExtra")
        String clientExtra;
        @JsonProperty(value = "params")
        Params params;
        class Params{
            @JsonProperty(value = "ticker")
            String ticker;
        }
    }

    @Override
    public String toString() {
        return "KakaoChatbotResponse{" +
                "intent=" + intent +
                ", userRequest=" + userRequest +
                ", action=" + action +
                '}';
    }
}