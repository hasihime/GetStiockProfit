package com.hasi.GetStockProfit.Application;

import com.hasi.GetStockProfit.Domain.Response.Profit;
import com.hasi.GetStockProfit.Domain.Response.InterStockResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CalculationProfitService {

    public Profit calculateProfit(String ticker, InterStockResponse[] arr) {
        String lowdate = arr[0].getDate();
        String highdate = arr[0].getDate();
        String curlowdate = lowdate;
        float low = arr[0].getClose();
        float high = arr[0].getClose();
        float result = high - low;

        for (int i = 1; i < arr.length; i++) {
            if (high < arr[i].getClose()) {
                high = arr[i].getClose();
                if (result < high - low) {
                    result = high - low;
                    lowdate = curlowdate;
                    highdate = arr[i].getDate();
                }
            }
            if (low > arr[i].getClose()) {
                low = arr[i].getClose();
                curlowdate = arr[i].getDate();
                high = arr[i].getClose();
            }
        }
        // 이익이 나지 않는다는 것은 시작일부터 끝일까지 동일하거나 중간에 올라가는 것 없는 하향세일경우임
        String etc = "";
        if (result == 0f) {
            etc = "You can never get profit in 180 days.";
            if (lowdate.equals(curlowdate)) {
                lowdate = highdate;
            } else
                lowdate = curlowdate;
        }
        return new Profit(ticker, lowdate.split("T")[0], highdate.split("T")[0], result, etc );
    }
}
