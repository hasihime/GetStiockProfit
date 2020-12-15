package com.hasi.GetStockProfit.Domain.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Profit {
	
	String ticker;
	String lowdate;
	String highdate;
	float maxprofit;
	String etc;

	public Profit(String ticker, String etc) {
		super();
		this.ticker = ticker;
		this.etc = etc;
	}
	
	/*
	 * {
	 * "ticker" : "AAPL",
	 * "lowdate" : "2020-07-01",
	 * "highdate" : "2020-08-31",
	 * "maxprofit" : 10.0f,
	 * "etc" : "",
	 *  "links": [
     *  {
     *  "rel": "self"
     *  "href": ��http://localhost:8080/accounts/1"
     *  },
     *  {
     *  "rel": "withdraw",
     *  "href": "http://localhost:8080/accounts/1/withdraw"
     *  },
     *  {
     *  "rel":"transfer",
     *  "href":��http://localhost:8080/accounts/1/transfer"
     *  }
     * ]
     *}
	 */
}
