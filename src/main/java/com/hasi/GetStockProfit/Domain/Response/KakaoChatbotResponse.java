package com.hasi.GetStockProfit.Domain.Response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

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
    @JsonProperty(value = "bot")
    Bot bot;

    public Map<String, String> getTciker() {
        return action.params;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    class Intent {
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
    class UserRequest {
        @JsonProperty(value = "timezone")
        String timezone;
        @JsonProperty(value = "params")
        Params params;
        @JsonProperty(value = "block")
        Block block;
        @JsonProperty(value = "utterance")
        String utterance;
        @JsonProperty(value = "lang")
        String lang;
        @JsonProperty(value = "user")
        User user;

        class Params {
            @JsonProperty(value = "surface")
            String surface;
            @JsonProperty(value = "ignoreMe")
            String ignoreMe;
        }

        class Block {
            @JsonProperty(value = "id")
            String id;
            @JsonProperty(value = "name")
            String name;
        }

        class User {
            @JsonProperty(value = "id")
            String id;
            @JsonProperty(value = "type")
            String type;
            @JsonProperty(value = "properties")
            List<Properties> properties;

            class Properties {
                @JsonProperty(value = "plusfriendUserKey")
                String plusfriendUserKey;
                @JsonProperty(value = "appUserId")
                String appUserId;
                @JsonProperty(value = "isFriend")
                boolean isFriend;
            }
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    class Bot {
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
    class Action {
        @JsonProperty(value = "name")
        String name;
        @JsonProperty(value = "clientExtra")
        Map<String, String> clientExtra;
        @JsonProperty(value = "params")
        Map<String, String> params;
        @JsonProperty(value = "id")
        String id;
        @JsonProperty(value = "detailParams")
        Map<String, String> detailParams;

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