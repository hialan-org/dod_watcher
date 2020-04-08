package usf.sdlc.service;

import usf.sdlc.dao.UserProfitRepository;
import usf.sdlc.dao.UserRepository;
import usf.sdlc.dao.UserStockActivityRepository;
import usf.sdlc.dao.UserStockRepository;
import usf.sdlc.form.AddStocksForm;
import usf.sdlc.form.StockActivityForm;
import usf.sdlc.model.UserProfit;
import usf.sdlc.model.UserStock;
import usf.sdlc.model.UserStockActivity;
import usf.sdlc.model.UserStockId;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserStockActivityServiceImpl implements UserStockActivityService {

    @Inject
    UserStockActivityRepository userStockActivityRepository;
    @Inject
    UserStockRepository userStockRepository;

    @Override
    public void saveAll(long userId, List<StockActivityForm> userStockActivities) {
        List<UserStockActivity> userStockActivitiesModel = new ArrayList<>();
        List<UserStock> userStocksModel = new ArrayList<>();
//        UserProfit userProfit = new UserProfit();
//        double investAmount = 0;

        //Parse from AddStocksForm to List of UserStock and List of UserStockActivity
        for (StockActivityForm stockActivityForm : userStockActivities) {
            long stockId = stockActivityForm.getStockId();
            double stockPrice = stockActivityForm.getStockPrice();
            int stockQuantity = stockActivityForm.getStockQuantity();
            boolean isBuy = stockActivityForm.isBuy();
            userStockActivitiesModel.add(
                    new UserStockActivity(
                            userId, stockId,
                            stockQuantity, stockPrice,
                            new Timestamp(System.currentTimeMillis()), 1
                    )
            );
            Optional<UserStock> userStock = userStockRepository
                    .findById(new UserStockId(userId, stockId));
            if (userStock.isPresent()) { //If userId - stockId already in db, update it
                if (isBuy) {
                    //Calculate new average price for the stock own by user
                    int newQuantity = userStock.get().getStockQuantity() + stockQuantity;
                    double newAveragePrice = (userStock.get().getStockAveragePrice() * userStock.get().getStockQuantity() +
                            stockQuantity * stockPrice) /
                            newQuantity;
                    userStock.get().setStockAveragePrice(newAveragePrice);
                    userStock.get().setStockQuantity(newQuantity);
                } else {
                    int newQuantity = userStock.get().getStockQuantity() - stockQuantity;
                    if (newQuantity == 0) { //Reset average price if stock quantity is 0
                        userStock.get().setStockAveragePrice(0);
                    }
                    userStock.get().setStockQuantity(newQuantity);
                }
                if(userStock.get().getStockQuantity() == 0){
                    userStockRepository.delete(userStock.get());
                } else {
                    userStockRepository.update(userStock.get());
                }
            } else { //If userId - stockId not in db, create new UserStock and add to list
                UserStock tmp = new UserStock(new UserStockId(userId, stockId), stockPrice, stockQuantity, 1);
                userStocksModel.add(tmp);
            }
        }
        if (!userStocksModel.isEmpty()) {
            userStockRepository.saveAll(userStocksModel);
        }
        userStockActivityRepository.saveAll(userStockActivitiesModel);
    }
}
