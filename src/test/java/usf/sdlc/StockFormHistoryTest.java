package usf.sdlc;

import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import usf.sdlc.form.StockHistoryForm;
import usf.sdlc.model.StockHistory;
import usf.sdlc.service.StockHistoryService;

import javax.inject.Inject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
public class StockFormHistoryTest {
    @Inject
    StockHistoryService stockHistoryService;

    @Test
    void testGetStockHistoryListFromDate() {
        System.out.println("Starting testFetchAllFromStockTable");
        List<StockHistoryForm> stockHistoryList = new ArrayList<>();
        try {
            stockHistoryList = stockHistoryService
                    .getTopYieldStockByDate(new SimpleDateFormat("yyyy-MM-dd")
                    .parse("2020-04-01"), 10);
            System.out.println(stockHistoryList);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        assertEquals(10, stockHistoryList.size());
    }
}
