package com.hasi.GetStockProfit.Domain.Request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KakaoSkillRequest {
    @JsonProperty(value = "intent")
    Intent intent;
    @JsonProperty(value = "userRequest")
    UserRequest userRequest;
    @JsonProperty(value = "action")
    Action action;
    @JsonProperty(value = "bot")
    Bot bot;

    public String getUtterance(){
        return userRequest.getUtterance();
    }
    public void makeUserRequest(){
        this.userRequest=new UserRequest();
    }

    public void makeUtterance(String utterance){
        this.userRequest.utterance=utterance;
    }



    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Intent {
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


        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        @JsonIgnoreProperties(ignoreUnknown = true)
        @JsonInclude(JsonInclude.Include.NON_NULL)
        class Params {
            @JsonProperty(value = "surface")
            String surface;
            @JsonProperty(value = "ignoreMe")
            String ignoreMe;
        }

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        @JsonIgnoreProperties(ignoreUnknown = true)
        @JsonInclude(JsonInclude.Include.NON_NULL)
        class Block {
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
        class User {
            @JsonProperty(value = "id")
            String id;
            @JsonProperty(value = "type")
            String type;
            @JsonProperty(value = "properties")
            Object properties;

            @Data
            @AllArgsConstructor
            @NoArgsConstructor
            @JsonIgnoreProperties(ignoreUnknown = true)
            @JsonInclude(JsonInclude.Include.NON_NULL)
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
        Map<String, Object> detailParams;
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