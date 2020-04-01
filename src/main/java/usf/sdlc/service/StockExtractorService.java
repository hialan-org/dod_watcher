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

            System.out.println("Stock ID:"+stocksEntityMap.get(stockDetailKey).getStockId());

            s.setStock(stocksEntityMap.get(stockDetailKey));
            s.setLatestTime(getSqlDateFromUnixTime(stockDetails.get(stockDetailKey).getQuote().getLatestUpdate())); // changing here to resolve correct date issue
            s.setLatestPrice(stockDetails.get(stockDetailKey).getQuote().getLatestPrice());
            s.setDividendYield(stockDetails.get(stockDetailKey).getStats().getDividendYield());
            System.out.println(s.toString());
            stocksHistory.add(s);
        }
        return stocksHistory;
    }

    private java.sql.Date getSqlDateFromUnixTime(String timeStr) {
        long timestamp = Long.parseLong(timeStr);
        java.sql.Date date=new java.sql.Date(timestamp);
        return date;
    }

//    private java.sql.Date getSqlDate(String timeStr) {
//        String[] timeStrArr = timeStr.split(" ");
//        if (timeStrArr.length == 3) {
//            timeStrArr[1] = timeStrArr[1].substring(0, timeStrArr[1].length()-1);
//        } else {
//            timeStrArr = new String[]{"January", "1", "2000"};
//        }
//        Date date = null;
//        try {
//            date = new SimpleDateFormat("yyyy-MMMM-dd").parse(timeStrArr[2]+"-"+timeStrArr[0]+"-"+timeStrArr[1]);
//        } catch (ParseException e) {
//            System.out.println("Parse Exception in getTimeStamp func, "+ e.getMessage());
//        }
//        assert date != null;
//        return new java.sql.Date(date.getTime());
//    }



}// end of class