package usf.sdlc.service;

import usf.sdlc.dao.StockHistoryRepository;
import usf.sdlc.dao.UserStockRepository;
import usf.sdlc.form.OwnedStockForm;
import usf.sdlc.model.StockHistory;
import usf.sdlc.model.UserStock;
import usf.sdlc.model.UserStockId;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class UserStockServiceImpl implements UserStockService {
    @Inject
    UserStockRepository userStockRepository;
    @Inject
    StockHistoryRepository stockHistoryRepository;

    @Override
    public List<OwnedStockForm> findOwnedStock(long userId) {
        List<OwnedStockForm> result = new ArrayList<>();
        System.out.println("Find owned stocks for userId: " + userId);
        List<UserStock> userStocks = userStockRepository.findByUserIdAndIsOwned(userId, 1);
        for(int i=0;i<userStocks.size();i++){
            UserStock userStock = userStocks.get(i);
            try{
                StockHistory stockHistory = stockHistoryRepository
                        .findLatestByStockId(userStock.getUserStockId().getStockId());
                OwnedStockForm ownedStockForm = new OwnedStockForm(
                        stockHistory.getStockId(), stockHistory.getStock().getSymbol(),
                        userStock.getStockAveragePrice(), userStock.getStockQuantity(),
                        stockHistory.getLatestPrice());
                result.add(ownedStockForm);
            } catch (Exception e){
                System.out.println(e);
                return null;
            }
        }
        System.out.println("Number of owned stocks: " + result.size());
        return result;
    }
}
