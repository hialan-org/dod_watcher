package usf.sdlc.service;

import usf.sdlc.form.AddStocksForm;
import usf.sdlc.form.StockActivityForm;
import usf.sdlc.form.UserStockForm;
import usf.sdlc.model.UserStock;
import usf.sdlc.model.UserStockActivity;

import java.util.List;

public interface UserStockActivityService {
    List<UserStockForm> saveAll(long userId, List<StockActivityForm> userStockActivities);
}
