package usf.sdlc.service;

import usf.sdlc.dao.StockHistoryRepository;
import usf.sdlc.dao.UserStockRepository;
import usf.sdlc.model.StockHistory;
import usf.sdlc.model.UserStock;

import javax.inject.Inject;
import java.util.*;

public class UserProfitServiceImpl implements UserProfitService {

    // requires param - userId, date
    // on given date profit of a userId will be calculated by:
    // 1. get stocks details (userId, stockId, stock_average_price, stock_quantity and is_owned=1) held  by userId from table - user_stock
    // 2. get stock prices (latest_price) at given date for all user held stockIds from table - stock_history
    // 3. take difference of stock_average_price and latest_price for all unique stocks
    // 4. Profit/Loss for each stock is the no of stock held * difference
    // 5. Result is the sum of Profit for all stocks held by user

    @Inject
    UserStockRepository userStockRepository;

    @Inject
    StockHistoryRepository stockHistoryRepository;

    @Override
    public float getUserProfitByDate(Long userId, Date date) {
        return 0;
    }

    public List<UserStock> getUserStocks(Long userId){
        Iterable<UserStock> usersStocks = userStockRepository.findAll();
        List<UserStock> userStocks = new ArrayList<>();
        for (UserStock s : usersStocks) {
            if (s.getUserStockId().getUserId() == userId & s.getIsOwned()==1){
                userStocks.add(s);
            }
        }
        return userStocks;
    }

    public Map<Long, StockHistory> getLatestStockDetails(Date d) {
        if(d == null) {
            d = stockHistoryRepository.customFindLatestDate();
        }
        List<StockHistory> ss = stockHistoryRepository.customFindStocksHistoryOnDate(d);
        Map<Long, StockHistory> m = new HashMap<>();
        for(StockHistory s : ss) {
            m.put(s.getStockId(), s);
        }
        return m;
    }

    public Map<Long, Double> createProfitDetails(List<UserStock> userStocks, Map<Long, StockHistory> stocksHistory) {
        Map<Long, Double> userEachStockProfit = new HashMap<>();
        for(UserStock userStock : userStocks){
            Long userStock_stockId = userStock.getUserStockId().getStockId();
            StockHistory stockHistory = stocksHistory.get(userStock_stockId);
            double priceDifference = stockHistory.getLatestPrice() - userStock.getStockAveragePrice();
            double profit = priceDifference * userStock.getStockQuantity();
            userEachStockProfit.put(userStock_stockId, profit);
        }
        return userEachStockProfit;
    }
}
