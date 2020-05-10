package usf.sdlc;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.annotation.MicronautTest;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import usf.sdlc.form.StockForm;
import usf.sdlc.service.StockExtractorService;

import javax.inject.Inject;
import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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
        System.out.println("Starting testGetStockDetailsFromOutside");
        // forming uri to hit IEX endpoint // todo - get token from github secret
        String symStr = "dow,aapl";
        String uri = "https://cloud.iexapis.com/v1/stock/market/batch?types=quote,stats&symbols="+symStr+"&token=pk_76512460ba7a434eb1aff6f1e40f0f1a";
        HttpRequest<String> request = HttpRequest.GET(uri);
        String body = client.toBlocking().retrieve(request);

        //// converting HTTP response to java object
        Type type = new TypeToken<HashMap<String, StockForm>>(){}.getType();
        Gson gson = new Gson();
        //System.out.println("BODY : "+ body);
        HashMap<String, StockForm> stockDetails = gson.fromJson(String.valueOf(body), type);

        assertEquals(2, stockDetails.size());

        assertEquals(null, stockDetails.get("dow"));
        assertNotEquals(null, stockDetails.get("DOW"));
        assertEquals("DOW", stockDetails.get("DOW").getQuote().getSymbol());
        assertEquals(null, stockDetails.get("aapl"));
        assertNotEquals(null, stockDetails.get("AAPL"));
        assertEquals("AAPL", stockDetails.get("AAPL").getQuote().getSymbol());
    }

    @Test
    void testRunExtractor() {
//        System.out.println("Starting testFetch2");
//        //We invoke the controller and retrieve the response.
//        StockExtractorResponse response = client.toBlocking().retrieve(HttpRequest.GET("/run-extractor"), StockExtractorResponse.class);
//        String  q = response.getMessage();
//        System.out.println(q);
//        assertEquals("Success!", q);
        System.out.println("Starting testRunExtractor");
        String uri = "http://127.0.0.1:3000/run-extractor";
        HttpGet request = new HttpGet(uri);
        String result = "";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            // Get HttpResponse Status
            HttpEntity entity = response.getEntity();
            Header headers = entity.getContentType();
            if (entity != null) {
                // return it as a String
                result = EntityUtils.toString(entity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //// converting HTTP response to java object

        assertEquals(result.contains("Success"),true);
    }

//    @Test
//    void testFetch3() {
//        System.out.println("Starting testFetch3");
//        StockExtractorResponse response = client.toBlocking().retrieve(HttpRequest.GET("/run-extractor"), StockExtractorResponse.class);
//        List<StockHistory> q = response.getStocksHistory();
//        System.out.println(q);
//        assertEquals(30, q.size());
//    }

    @Test
    void testConvertToTimestamp() throws ParseException {
        String timeStr = "February 20, 2020";
        String[] timeStrArr = timeStr.split(" ");
        if (timeStrArr.length > 0) {
            timeStrArr[1] = timeStrArr[1].substring(0, timeStrArr[1].length()-1);
        }
        Date date = new SimpleDateFormat("yyyy-MMMM-dd").parse(timeStrArr[2]+"-"+timeStrArr[0]+"-"+timeStrArr[1]);
        Timestamp timestamp = new Timestamp(date.getTime());
        System.out.println("date:"+date);
        assertEquals("2020-02-20 00:00:00.0",timestamp.toString());

    }
}