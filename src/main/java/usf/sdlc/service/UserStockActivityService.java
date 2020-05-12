package usf.sdlc.service;

import usf.sdlc.form.StockActivityForm;
import usf.sdlc.form.UserStockForm;

import java.util.Date;
import java.util.List;

public interface UserStockActivityService {
    List<UserStockForm> saveAll(long userId, List<StockActivityForm> userStockActivities);
    Date getLatestStockActivityTime();
}
