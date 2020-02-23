package usf.sdlc;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import usf.sdlc.form.Stock;
import usf.sdlc.form.StockExtractorResponse;
import usf.sdlc.form.StockHistoryForm;
import usf.sdlc.model.StockHistory;
import usf.sdlc.service.StockExtractorService;

import javax.inject.Inject;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@MicronautTest
public class StockExtractorControllerTest {
    @Inject
    EmbeddedServer server;

    @Inject
    @Client("/")
    HttpClient client;

    @Inject
    StockExtractorService stockExtractorService;

    @Test
    void testGetStockDetailsFromOutside() {
        System.out.println("Starting testFetchAllFromStockTable");
        HashMap<String, Stock> s = stockExtractorService.getStockDetailsFromOutside("dow,aapl");
        assertEquals(2, s.size());
    }

//    @Test
//    void testFetch2() {
//        System.out.println("Starting testFetch2");
//        StockExtractorResponse response = client.toBlocking().retrieve(HttpRequest.GET("/run-extractor"), StockExtractorResponse.class);
//        String  q = response.getMessage();
//        System.out.println(q);
//        assertEquals("Success!", q);
//    }

//    @Test
//    void testFetch3() {
//        System.out.println("Starting testFetch3");
//        StockExtractorResponse response = client.toBlocking().retrieve(HttpRequest.GET("/run-extractor"), StockExtractorResponse.class);
//        List<StockHistory> q = response.getStocksHistory();
//        System.out.println(q);
//        assertEquals(30, q.size());
//    }

//    @Test
//    void testConvertToTimestamp() throws ParseException {
//        String timeStr = "February 20, 2020";
//        String[] timeStrArr = timeStr.split(" ");
//        if (timeStrArr.length > 0) {
//            timeStrArr[1] = timeStrArr[1].substring(0, timeStrArr[1].length()-1);
//        }
//        Date date = new SimpleDateFormat("yyyy-MMMM-dd").parse(timeStrArr[2]+"-"+timeStrArr[0]+"-"+timeStrArr[1]);
//        Timestamp timestamp = new Timestamp(date.getTime());
//        System.out.println(timestamp.toString());
//
//    }
}