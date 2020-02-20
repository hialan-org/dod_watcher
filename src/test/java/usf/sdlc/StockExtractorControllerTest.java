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
import usf.sdlc.form.StockForm;

import javax.inject.Inject;

import java.lang.reflect.Type;
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

    @Test
    void testFetch1() {
        System.out.println("Starting testFetch1");
        String uri = "https://cloud.iexapis.com/v1/stock/market/batch?types=quote,stats&symbols=DOW,XOM,IBM,VZ,CVX,PFE,MMM,WBA,CSCO,KO&token=pk_76512460ba7a434eb1aff6f1e40f0f1a";
        HttpRequest<String> request = HttpRequest.GET(uri);
        String body = client.toBlocking().retrieve(request);
        Type type = new TypeToken<HashMap<String, Stock>>(){}.getType();
        Gson gson = new Gson();
        HashMap<String, Stock> stockMap = gson.fromJson(body, type);
        /////
        List<StockForm> stockForms = new ArrayList<>(stockMap.size());
        for ( String sym : stockMap.keySet()) { // assuming size of stockMap and stockForms are same
            StockForm s = new StockForm();
            s.setSymbol(sym);
            s.setLatestPrice(stockMap.get(sym).getQuote().getLatestPrice());
            s.setLatestTime(stockMap.get(sym).getQuote().getLatestTime());
            s.setDividendYield(stockMap.get(sym).getStats().getDividendYield());
            stockForms.add(s);
        }
        /////
        assertEquals(10, stockForms.size());
    }

    @Test
    void testFetch2() {
        System.out.println("Starting testFetch2");
        StockExtractorResponse response = client.toBlocking().retrieve(HttpRequest.GET("/run-extractor"), StockExtractorResponse.class);
        String  q = response.getMessage();
        System.out.println(q);
        assertEquals("Success!", q);
    }

    @Test
    void testFetch3() {
        System.out.println("Starting testFetch3");
        StockExtractorResponse response = client.toBlocking().retrieve(HttpRequest.GET("/run-extractor"), StockExtractorResponse.class);
        List<StockForm> q = response.getStockForms();
        System.out.println(q);
        assertEquals(10, q.size());
    }
}