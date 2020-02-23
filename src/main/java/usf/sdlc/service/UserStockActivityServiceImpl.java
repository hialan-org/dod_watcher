package usf.sdlc.service;

import usf.sdlc.dao.UserRepository;
import usf.sdlc.dao.UserStockActivityRepository;
import usf.sdlc.dao.UserStockRepository;
import usf.sdlc.form.AddStocksForm;
import usf.sdlc.form.StockActivityForm;
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
    public void saveAll(long userId, AddStocksForm userStockActivities) {
        List<UserStockActivity> userStockActivitiesModel = new ArrayList<>();
        List<UserStock> userStocksModel = new ArrayList<>();

        //Parse from AddStocksForm to List of UserStock and List of UserStockActivity
        for (StockActivityForm stockActivityForm:userStockActivities.getStockActivityForms()) {
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
            if(userStock.isPresent()){ //If userId - stockId already in db, update it
                System.out.println(isBuy);
                if(isBuy){
                    //Calculate new average price for the stock own by user
                    double newAveragePrice = (userStock.get().getStockAveragePrice() * stockQuantity +
                            stockActivityForm.getStockQuantity() * stockPrice) /
                            (userStock.get().getStockQuantity() + stockQuantity);
                    userStock.get().setStockAveragePrice(newAveragePrice);
                    userStock.get().setStockQuantity(userStock.get().getStockQuantity() + stockQuantity);
                } else {
                    userStock.get().setStockQuantity(userStock.get().getStockQuantity() - stockQuantity);
                }
                userStockRepository.update(userStock.get());
            } else { //If userId - stockId not in db, create new UserStock and add to list
                UserStock tmp = new UserStock(new UserStockId(userId, stockId), stockPrice, stockQuantity, 1);
                userStocksModel.add(tmp);
            }
        }
        if(!userStocksModel.isEmpty()){
            userStockRepository.saveAll(userStocksModel);
        }
        userStockActivityRepository.saveAll(userStockActivitiesModel);
    }
}
