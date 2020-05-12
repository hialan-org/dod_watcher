package usf.sdlc.service;

import usf.sdlc.dao.StockHistoryRepository;
import usf.sdlc.dao.UserProfitRepository;
import usf.sdlc.dao.UserStockRepository;
import usf.sdlc.model.StockHistory;
import usf.sdlc.model.UserProfit;
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

    @Inject
    UserProfitRepository userProfitRepository;

    @Override
    public boolean saveAllUserProfit(Date date) {
        Iterable<UserStock> si = userStockRepository.findAll();
        Set<Long> allUsers = new HashSet<>();
        for (UserStock us : si) {
            allUsers.add(us.getUserStockId().getUserId());
        }

        for(long uid : allUsers) {
            this.saveUserProfit(uid, date);
        }

        return true;
    }

    @Override
    public Iterable<UserProfit> saveUserProfit(Long userId, Date date) {
        List<UserStock> s = this.getUserStocks(userId);
        Map<Long, StockHistory> m = this.getLatestStockDetails(date);
        List<UserProfit> p = this.createProfitDetails(s, m);
        return this.overwriteAndSave(p);
//        return userProfitRepository.saveAll(p);
    }

    @Override
    public List<UserProfit> getUserProfitHistory(long userId, long stockId, Date startDate, Date endDate) {
        List<UserProfit> i = userProfitRepository.customGetUserProfitHistory(userId, stockId, startDate, endDate);
        return i;
    }

    private List<UserStock> getUserStocks(Long userId){ // from user_stock table
        Iterable<UserStock> usersStocks = userStockRepository.findAll();
        List<UserStock> userStocks = new ArrayList<>();
        for (UserStock s : usersStocks) {
            if (s.getUserStockId().getUserId() == userId & s.getIsOwned()==1){
                userStocks.add(s);
            }
        }
        return userStocks;
    }

    private Map<Long, StockHistory> getLatestStockDetails(Date d) { // from stock_history table
        if(d == null) {
            d = stockHistoryRepository.customFindLatestDate();
        }
        List<StockHistory> ss = stockHistoryRepository.customFindStocksHistoryOnDate(d);
        Map<Long, StockHistory> m = new HashMap<>();
        for(StockHistory s : ss) {
            m.put(s.getStock().getStockId(), s);
        }
        return m;
    }

    private List<UserProfit> createProfitDetails(List<UserStock> userStocks, Map<Long, StockHistory> stocksHistory) {
        List<UserProfit> userStockProfit = this.getUserStockProfit(userStocks, stocksHistory);//new ArrayList<>();
        UserProfit userOverallProfit = this.getUserOverallProfit(userStockProfit);//new UserProfit();
        userStockProfit.add(userOverallProfit);
        return userStockProfit;
    }

    private List<UserProfit> getUserStockProfit(List<UserStock> userStocks, Map<Long, StockHistory> stocksHistory) {
        List<UserProfit> userStockProfit = new ArrayList<>();
        for(UserStock userStock : userStocks){
            UserProfit up = new UserProfit();
            up.setUserId(userStock.getUserStockId().getUserId());
            up.setStockId(userStock.getUserStockId().getStockId());
            StockHistory stockHistory = stocksHistory.get(up.getStockId());
            up.setDate(stockHistory.getLatestTime());
            up.setInvestedAmount(userStock.getStockQuantity() * userStock.getStockAveragePrice());
            up.setTotalAmount(userStock.getStockQuantity() * stockHistory.getLatestPrice());
            up.setUserProfit(up.getTotalAmount() - up.getInvestedAmount());
            userStockProfit.add(up);
        }
        return userStockProfit;
    }

    private UserProfit getUserOverallProfit(List<UserProfit> userStockProfit) {
        UserProfit userOverallProfit = new UserProfit();
        if (userStockProfit.size() > 0) {
            userOverallProfit.setUserId(userStockProfit.get(0).getUserId());
            userOverallProfit.setDate(userStockProfit.get(0).getDate());
            userOverallProfit.setInvestedAmount(0);
            userOverallProfit.setTotalAmount(0);
            userOverallProfit.setUserProfit(0);
        }
        for (UserProfit userProfit : userStockProfit) {
            userOverallProfit.setInvestedAmount(userOverallProfit.getInvestedAmount() + userProfit.getInvestedAmount());
            userOverallProfit.setTotalAmount(userOverallProfit.getTotalAmount() + userProfit.getTotalAmount());
            userOverallProfit.setUserProfit(userOverallProfit.getUserProfit() + userProfit.getUserProfit());
        }
        return userOverallProfit;
    }

    private List<UserProfit> overwriteAndSave(List<UserProfit> userProfit) {
        List<UserProfit> userProfitSaved = new ArrayList<>();
        if (userProfit.size() > 0) {
            long uid = userProfit.get(0).getUserId();
            Date d = userProfit.get(0).getDate();
            // delete existing rows at the given date in user_profit table
            int numRowsDeleted = userProfitRepository.customDeleteUserProfitOnDate(uid, d);
            System.out.println("numRowsDeleted: "+ numRowsDeleted);
            // adding new rows to user_profit table
            userProfitSaved = userProfitRepository.saveAll(userProfit);
            System.out.println("rows of userProfitSaved: "+ userProfitSaved.size());
        }
        return userProfitSaved;

    }

    public Double countTotalAmountOfUserMoney(){
        Double  totalAmountOfUserMoney= userProfitRepository.countTotalAmountOfUserMoney().get(0);
        System.out.println("totalAmountOfUserMoney:"+totalAmountOfUserMoney);
        return totalAmountOfUserMoney;
    }

}
