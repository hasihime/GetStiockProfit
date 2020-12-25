package com.hasi.GetStockProfit.Application;

import com.hasi.GetStockProfit.Domain.Response.Profit;
import com.hasi.GetStockProfit.Domain.Response.InterStockResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GetStockProfitServiceTest {

    private static InterStockResponse[] testDataarr;

    @Autowired
    private GetStockProfitService getStockProfitService;

    @BeforeAll
    static void setting() {
        testDataarr = new InterStockResponse[10];
        testDataarr[0] = new InterStockResponse("2020-09-01", 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        testDataarr[1] = new InterStockResponse("2020-09-02", 11, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        testDataarr[2] = new InterStockResponse("2020-09-03", 12, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        testDataarr[3] = new InterStockResponse("2020-09-04", 9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        testDataarr[4] = new InterStockResponse("2020-09-05", 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        testDataarr[5] = new InterStockResponse("2020-09-06", 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        testDataarr[6] = new InterStockResponse("2020-09-07", 13, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        testDataarr[7] = new InterStockResponse("2020-09-08", 15, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        testDataarr[8] = new InterStockResponse("2020-09-09", 15, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        testDataarr[9] = new InterStockResponse("2020-09-10", 15, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    }

    @Test
    @DisplayName("연속 상승경우")
    public void alwaysUpword() {
        InterStockResponse[] test = new InterStockResponse[3];
        for (int i = 0; i < test.length; i++) {
            test[i] = testDataarr[i];
        }
        Profit result = getStockProfitService.getprofit("testticker",test);
        assertEquals(2f, result.getMaxprofit());
        assertEquals("2020-09-01", result.getLowdate());
        assertEquals("2020-09-03", result.getHighdate());
    }

    @Test
    @DisplayName("연속 하강경우")
    public void alwaysDownword() {
        InterStockResponse[] test = new InterStockResponse[3];
        for (int i = 0; i < test.length; i++) {
            test[i] = testDataarr[i + 3];
        }
        Profit result = getStockProfitService.getprofit("testticker",test);
        assertEquals(0f, result.getMaxprofit());
        assertEquals("2020-09-06", result.getLowdate());
        assertEquals("2020-09-04", result.getHighdate());
    }

    @Test
    @DisplayName("변화가 없는경우")
    public void AlwaysEquals() {
        InterStockResponse[] test = new InterStockResponse[3];
        for (int i = 0; i < test.length; i++) {
            test[i] = testDataarr[i + 7];
        }
        Profit result = getStockProfitService.getprofit("testticker",test);
        assertEquals(0f, result.getMaxprofit());
        assertEquals("2020-09-08", result.getLowdate());
        assertEquals("2020-09-08", result.getHighdate());
    }

    @Test
    @DisplayName("최저가가 갱신되었지만 그 뒤에 최대 이익이 나지 않은 경우")
    public void renewmin() {
        InterStockResponse[] test = new InterStockResponse[6];

        String mostLowDate = testDataarr[0].getDate();
        float MostLowValue = testDataarr[0].getClose();
        for (int i = 0; i < test.length; i++) {
            test[i] = testDataarr[i];
            if (MostLowValue > test[i].getClose()) {
                MostLowValue = test[i].getClose();
                mostLowDate = test[i].getDate();
            }
        }
        Profit result = getStockProfitService.getprofit("testticker",test);
        assertEquals("2020-09-06", mostLowDate);
        assertEquals(2f, result.getMaxprofit());
        assertEquals("2020-09-01", result.getLowdate());
        assertEquals("2020-09-03", result.getHighdate());
    }

    @Test
    @DisplayName("최저가와 최고가가 갱신된 경우")
    public void renewMinandMax() {
        InterStockResponse[] test = new InterStockResponse[8];
        for (int i = 0; i < test.length; i++) {
            test[i] = testDataarr[i];
        }
        Profit result = getStockProfitService.getprofit("testticker",test);
        assertEquals(8f, result.getMaxprofit());
        assertEquals("2020-09-06", result.getLowdate());
        assertEquals("2020-09-08", result.getHighdate());
    }


    @Test
    @DisplayName("전체")
    public void allarr() {
        Profit result = getStockProfitService.getprofit("testticker",testDataarr);
        assertEquals(8f, result.getMaxprofit());
        assertEquals("2020-09-06", result.getLowdate());
        assertEquals("2020-09-08", result.getHighdate());
    }
}