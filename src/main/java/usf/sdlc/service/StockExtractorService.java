package usf.sdlc.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import usf.sdlc.config.Constant;
import usf.sdlc.dao.StockHistoryRepository;
import usf.sdlc.form.StockForm;
import usf.sdlc.model.StockHistory;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.lang.reflect.Type;
import java.util.*;

@Singleton
public class StockExtractorService {
    // List of stock symbols
    //public final String[] SYMBOLS = {"DOW","XOM","IBM","VZ","CVX","PFE","MMM","WBA","CSCO","KO"};

    @Inject
    @Client("/")
    HttpClient client;

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
        HashMap<String, StockForm> stockDetails = getStockDetailsFromOutside(symStr);
        System.out.println("STOCK LIST SIZE: " + stockDetails.size());
        // converting Map to List of StockHistory (model) to put in StockHistory table
        ArrayList<StockHistory> stocksHistory = buildEntityListForStockHistory(stockDetails, stocksEntityMap);
        // putting stocksHistory in stock_history table
        List<StockHistory> stocksHistorySaved  = overwriteSave(stocksHistory);

        long endTime = System.currentTimeMillis();
        System.out.println("Total execution time: " + (endTime-startTime) + "ms");
        return stocksHistorySaved;
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
  
    private HashMap<String, StockForm> getStockDetailsFromOutside(String symStr) {
        // forming uri to hit IEX endpoint // todo - get token from github secret
        HashMap<String, StockForm> s = new HashMap<>();
        try {
            s = sendGet(symStr);
        } catch (Exception e) {
            System.out.println("Error in sendGet, "+ e.toString());
        }
        return s;
    }

    private HashMap<String, StockForm> sendGet(String symStr) throws Exception {
        String uri = "https://cloud.iexapis.com/v1/stock/market/batch?" +
                "types=quote,stats" +
                "&symbols="+symStr+
                "&filter=latestPrice,dividendYield,latestTime,latestUpdate"+
                "&token=" + Constant.IEX_TOKEN;
        HttpGet request = new HttpGet(uri);
        String result = "";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try (CloseableHttpResponse response = httpClient.execute(request)) {
            // Get HttpResponse Status
            //System.out.println(response.getStatusLine().toString());
            HttpEntity entity = response.getEntity();
            Header headers = entity.getContentType();
            //System.out.println(headers);
            if (entity != null) {
                // return it as a String
                result = EntityUtils.toString(entity);
                //System.out.println(result);
            }
        }
        //// converting HTTP response to java object
        Type type = new TypeToken<HashMap<String, StockForm>>(){}.getType();
        Gson gson = new Gson();
        return gson.fromJson(result, type);
    }

    private ArrayList<StockHistory> buildEntityListForStockHistory
            (HashMap<String, StockForm> stockDetails, HashMap<String, usf.sdlc.model.Stock> stocksEntityMap) {
        // converting Map to List of StockHistory (model) to put in StockHistory table
        ArrayList<StockHistory> stocksHistory = new ArrayList<>(stockDetails.size());
        for (String stockDetailKey : stockDetails.keySet()) {
            StockHistory s = new StockHistory();

            s.setStockId(stocksEntityMap.get(stockDetailKey).getStockId());
            s.setLatestTime(getSqlDateFromUnixTime(stockDetails.get(stockDetailKey).getQuote().getLatestUpdate())); // changing here to resolve correct date issue
            s.setLatestPrice(stockDetails.get(stockDetailKey).getQuote().getLatestPrice());
            s.setDividendYield(stockDetails.get(stockDetailKey).getStats().getDividendYield());
            stocksHistory.add(s);
        }
        return stocksHistory;
    }

    private List<StockHistory> overwriteSave(ArrayList<StockHistory> stocksHistory) {
        List<StockHistory> stocksHistorySaved = new ArrayList<>();
        if (stocksHistory.size() > 0) {
            Date d = stocksHistory.get(0).getLatestTime();
            // delete existing rows at the given date in stock_history table
            int numRowsDeleted = stockHistoryRepository.customDeleteStocksHistoryOnDate(d);
            // adding new rows to stock_history table
            stocksHistorySaved = stockHistoryRepository.saveAll(stocksHistory);
        }
        return stocksHistorySaved;
    }

    private java.sql.Date getSqlDateFromUnixTime(String timeStr) {
        long timestamp = Long.parseLong(timeStr);
        java.sql.Date date=new java.sql.Date(timestamp);
        return date;
    }



}// end of class