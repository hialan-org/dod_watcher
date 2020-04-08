package usf.sdlc.service;

import usf.sdlc.form.AddStocksForm;
import usf.sdlc.form.StockActivityForm;
import usf.sdlc.model.UserStockActivity;

import java.util.List;

public interface UserStockActivityService {
    void saveAll(long userId, List<StockActivityForm> userStockActivities);
}
