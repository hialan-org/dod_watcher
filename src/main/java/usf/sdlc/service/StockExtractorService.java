package usf.sdlc.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import org.checkerframework.common.reflection.qual.GetClass;
import usf.sdlc.form.Stock;
import usf.sdlc.form.StockForm;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Singleton
public class StockExtractorService {
    // List of stock symbols
    public final String[] SYMBOLS = {"DOW","XOM","IBM","VZ","CVX","PFE","MMM","WBA","CSCO","KO"}; // todo - get from data base

    @Inject
    @Client("/")
    RxHttpClient client;

    public StockExtractorService() {
    }

    public List<StockForm> fetchStockQuotes() {
        //todo : create a client and fetch results
        long startTime = System.currentTimeMillis();

        // building string of all stock symbols
        StringBuilder symStrB = new StringBuilder();
        for (String symbol : SYMBOLS) {
            symStrB.append(symbol).append(",");
        }
        String symStr = symStrB.toString();
        symStr = symStr.substring(0, symStr.length()-1);

        // forming uri to hit IEX endpoint // todo - get token from github secret
        String uri = "https://cloud.iexapis.com/v1/stock/market/batch?types=quote,stats&symbols="+symStr+"&token=pk_76512460ba7a434eb1aff6f1e40f0f1a";
        HttpRequest<String> request = HttpRequest.GET(uri);
        String body = client.toBlocking().retrieve(request);
        //// converting to java object
        Type type = new TypeToken<HashMap<String, Stock>>(){}.getType();
        Gson gson = new Gson();
        //System.out.println("BODY : "+ body);
        HashMap<String, Stock> stockMap = gson.fromJson(body, type);
        //// converting to List of StockForm
        List<StockForm> stockForms = new ArrayList<>(stockMap.size());
        for ( String sym : stockMap.keySet()) { // assuming size of stockMap and stockForms are same
            StockForm s = new StockForm();
            s.setSymbol(sym);
            s.setLatestPrice(stockMap.get(sym).getQuote().getLatestPrice());
            s.setLatestTime(stockMap.get(sym).getQuote().getLatestTime());
            s.setDividendYield(stockMap.get(sym).getStats().getDividendYield());
            stockForms.add(s);
        }
        ////
        long endTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime-startTime) + "ms");
        return stockForms;
    }

}// end of class