package usf.sdlc;

import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import usf.sdlc.model.StockHistory;
import usf.sdlc.model.UserStock;
import usf.sdlc.service.UserProfitServiceImpl;

import javax.inject.Inject;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
public class UserProfitTest {

    @Inject
    UserProfitServiceImpl userProfitServiceImpl;

    @Test
    void getUserStocksTest() {
        List<UserStock> s = userProfitServiceImpl.getUserStocks((long) 1);
        assertTrue(s.size()>0);
    }

    @Test
    void getStockDetailsTest() {
        Map<Long, StockHistory> m = userProfitServiceImpl.getLatestStockDetails(null);
        assertTrue(m.size() >= 30);
    }

    @Test
    void createProfitDetailsTest() {
        List<UserStock> s = userProfitServiceImpl.getUserStocks((long) 1);
        Map<Long, StockHistory> m = userProfitServiceImpl.getLatestStockDetails(null);
        Map<Long, Double> p = userProfitServiceImpl.createProfitDetails(s, m);
        assertEquals(s.size(), p.size());
    }


}