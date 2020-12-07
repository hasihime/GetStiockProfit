package com.hasi.GetStockProfit.Domain.Response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DomesticStockResponse {

    @JsonProperty("jsonrpc")
    String jsonrpc;
    @JsonProperty("result")
    Result result;
}

class Result{
    @JsonProperty("isuSrtCd")
    String isuSrtCd;
    @JsonProperty("hisLists")
    List<HisLists> hisLists;
}

class HisLists{
    @JsonProperty("BzDd")
    Date BzDd;
    @JsonProperty("trdPrc")
    Long trdPrc;
    @JsonProperty("cmpprevddTpCd")
    String cmpprevddTpCd;
    @JsonProperty("opnprc")
    Long opnprc;
    @JsonProperty("hgprc")
    Long hgprc;
    @JsonProperty("lwprc")
    Long lwprc;
    @JsonProperty("accTrdvol")
    Long accTrdvol;
    @JsonProperty("cmpprevddPrc")
    Long cmpprevddPrc;
}
