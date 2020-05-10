package usf.sdlc;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import usf.sdlc.controller.StockController;
import usf.sdlc.dao.StockHistoryRepository;
import usf.sdlc.form.StockExtractorResponse;
import usf.sdlc.model.StockHistory;

import javax.inject.Inject;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@MicronautTest
public class StockHistoryRepositoryTest {

    @Inject
    StockHistoryRepository stockHistoryRepository;

    @Inject
    StockController stockController;

    @Inject
    EmbeddedServer server;

    @Inject
    @Client("/")
    HttpClient client;

    @Test
    public void customFindStocksHistoryOnDateTest() {
        Date d = new Date(120, 3, 15);
        List<StockHistory> res =  stockHistoryRepository.customFindStocksHistoryOnDate(d);
        assertTrue(res.size()==31); //in a specific day, there should be 30 stocks.
        //d = new Date(120, 4, 8); // Why we don't have data in 05-08-2020 Friday?? Check it!
        //res =  stockHistoryRepository.customFindStocksHistoryOnDate(d);
        //assertTrue(res.size()==30); //in a specific day, there should be 30 stocks.
        d = new Date(120, 4, 7); //05-07-2020 Thursday. No data
        res =  stockHistoryRepository.customFindStocksHistoryOnDate(d);
        assertTrue(res.size()==31); //in a specific day, there should be 30 stocks.
        d = new Date(120, 4, 2); //05-02-2020 Saturday. No data
        res =  stockHistoryRepository.customFindStocksHistoryOnDate(d);
        assertTrue(res.size()==0); //in a specific day, there should be 30 stocks.


    }

    @Test
    void getStockHistoryForDateTest() {
        System.out.println("Starting getStockHistoryForDateTest");
        HttpResponse<StockExtractorResponse> resp =  stockController.getStockHistoryForDate("04-08-2020");
        assertEquals("Success!", Objects.requireNonNull(resp.body()).getMessage());
    }

    @Test
    void findLatestByStockIdTest() {
        long stockId=1;
        System.out.println("Starting findLatestByStockIdTest");
        StockHistory stockHistory =  stockHistoryRepository.findLatestByStockId(stockId);

        assertEquals(1, stockHistory.getStockId());
        assertEquals("MMM", stockHistory.getStock().getSymbol());

    }
}
