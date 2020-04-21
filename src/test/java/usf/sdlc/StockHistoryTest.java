package usf.sdlc;

import io.micronaut.http.HttpRequest;
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
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
public class StockHistoryTest {

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
        Date d = new Date(120, 3, 8);
        List<StockHistory> res =  stockHistoryRepository.customFindStocksHistoryOnDate(d);
        assertTrue(res.size()>0);
    }

    @Test
    void getStockHistoryForDateTest() {
        System.out.println("Starting getStockHistoryForDateTest");
        HttpResponse<StockExtractorResponse> resp =  stockController.getStockHistoryForDate("04-08-2020");
        assertEquals("Success!", Objects.requireNonNull(resp.body()).getMessage());
    }
}
