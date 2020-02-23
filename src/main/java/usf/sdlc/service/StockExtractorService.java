package usf.sdlc.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.reactivex.Flowable;
import usf.sdlc.dao.StockHistoryRepository;
import usf.sdlc.form.Stock;
import usf.sdlc.model.StockHistory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Singleton
public class StockExtractorService {
    // List of stock symbols
    //public final String[] SYMBOLS = {"DOW","XOM","IBM","VZ","CVX","PFE","MMM","WBA","CSCO","KO"};

    @Inject
    @Client("/")
    RxHttpClient client;

    @Inject
    StockService stockService;

    @Inject
    StockHistoryRepository stockHistoryRepository;


    public StockExtractorService() {
    }

    public List<StockHistory> fetchStockDetails() {
        long startTime = System.currentTimeMillis();

        // fetch All Rows From Stock Table
        HashMap<String, usf.sdlc.model.Stock> stocksEntityMap = fetchAllFromStockTable();
        // building string of all stock symbols
        String symStr = buildStockSymbolsStringForQuery(stocksEntityMap);
        // forming uri to hit IEX endpoint // todo - get token from github secret
        HashMap<String, Stock> stockDetails = getStockDetailsFromOutside(symStr);
        // converting Map to List of StockHistory (model) to put in StockHistory table
        List<StockHistory> stocksHistory = buildEntityListForStockHistory(stockDetails, stocksEntityMap);
        // putting stocksHistory in stock_history table
        stockHistoryRepository.saveAll(stocksHistory);

        long endTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime-startTime) + "ms");
        return stocksHistory;
    }

    private HashMap<String, usf.sdlc.model.Stock> fetchAllFromStockTable() {
        List<usf.sdlc.model.Stock> stockRows =  stockService.list();
        HashMap<String, usf.sdlc.model.Stock> stocksMap = new HashMap<>();
        for (usf.sdlc.model.Stock row : stockRows) {
            stocksMap.put(row.getSymbol(), row);
        }
        return stocksMap;
    }

    private String buildStockSymbolsStringForQuery(HashMap<String, usf.sdlc.model.Stock> stockEntities) {
        StringBuilder symStrB = new StringBuilder();
        for (String stockSym : stockEntities.keySet()) {
            symStrB.append(stockSym).append(",");
        }
        String symStr = symStrB.toString();
        symStr = symStr.substring(0, symStr.length()-1);
        return symStr;
    }

    public HashMap<String, Stock> getStockDetailsFromOutside(String symStr) {
        // forming uri to hit IEX endpoint // todo - get token from github secret
        String uri = "https://cloud.iexapis.com/v1/stock/market/batch?types=quote,stats&symbols="+symStr+"&token=pk_76512460ba7a434eb1aff6f1e40f0f1a";
        HttpRequest<String> request = HttpRequest.GET(uri);
        //String body = client.toBlocking().retrieve(request);
        Flowable<String> body = client.retrieve(request);
        //.retrieve(request);

        //// converting HTTP response to java object
        Type type = new TypeToken<HashMap<String, Stock>>(){}.getType();
        Gson gson = new Gson();
        //System.out.println("BODY : "+ body);
        //HashMap<String, Stock> stockDetails = gson.fromJson(body, type);

        return gson.fromJson(String.valueOf(body), type);
    }

    private ArrayList<StockHistory> buildEntityListForStockHistory
            (HashMap<String, Stock> stockDetails, HashMap<String, usf.sdlc.model.Stock> stocksEntityMap) {
        // converting Map to List of StockHistory (model) to put in StockHistory table
        ArrayList<StockHistory> stocksHistory = new ArrayList<>(stockDetails.size());
        for (String stockDetailKey : stockDetails.keySet()) {
            StockHistory s = new StockHistory();
            s.setStockId(stocksEntityMap.get(stockDetailKey).getStockId());
            s.setLatestTime(getTimeStamp(stockDetails.get(stockDetailKey).getQuote().getLatestTime()));
            s.setLatestPrice(stockDetails.get(stockDetailKey).getQuote().getLatestPrice());
            s.setDividendYield(stockDetails.get(stockDetailKey).getStats().getDividendYield());
            stocksHistory.add(s);
        }
        return stocksHistory;
    }

    private Timestamp getTimeStamp(String timeStr) {
        String[] timeStrArr = timeStr.split(" ");
        if (timeStrArr.length == 3) {
            timeStrArr[1] = timeStrArr[1].substring(0, timeStrArr[1].length()-1);
        } else {
            timeStrArr = new String[]{"January", "1", "2000"};
        }

        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MMMM-dd").parse(timeStrArr[2]+"-"+timeStrArr[0]+"-"+timeStrArr[1]);
        } catch (ParseException e) {
            System.out.println("Parse Exception in getTimeStamp func, "+ e.getMessage());
        }
        assert date != null;
        return new Timestamp(date.getTime());
    }

}// end of class