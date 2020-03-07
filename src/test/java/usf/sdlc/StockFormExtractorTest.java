package usf.sdlc;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import usf.sdlc.form.StockForm;
import usf.sdlc.model.StockHistory;
import usf.sdlc.service.StockExtractorService;

import javax.inject.Inject;

import java.lang.reflect.Type;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@MicronautTest
public class StockFormExtractorTest {
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
        // forming uri to hit IEX endpoint // todo - get token from github secret
        String symStr = "aapl,csco,baba";
        String uri = "https://cloud.iexapis.com/v1/stock/market/batch?types=quote,stats&symbols="+symStr+"&filter=latestPrice,dividendYield,latestTime,latestUpdate&token=pk_76512460ba7a434eb1aff6f1e40f0f1a";
        HttpRequest<String> request = HttpRequest.GET(uri);
        String body = client.toBlocking().retrieve(request);
//        Publisher<String> body = client.retrieve(request);
        //// converting HTTP response to java object
        Type type = new TypeToken<HashMap<String, StockForm>>(){}.getType();
        Gson gson = new Gson();
        //System.out.println("BODY : "+ body);
        HashMap<String, StockForm> stockDetails = gson.fromJson(String.valueOf(body), type);

        assertEquals(3, stockDetails.size());
    }

    @Test
    void fetchStockDetailsTest() {
        List<StockHistory> sh = stockExtractorService.fetchStockDetails();
        assertEquals(31, sh.size());
    }

}