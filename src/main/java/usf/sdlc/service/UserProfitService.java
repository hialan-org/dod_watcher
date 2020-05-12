package usf.sdlc.service;


import usf.sdlc.model.UserProfit;

import java.util.Date;

public interface UserProfitService {
    Iterable<UserProfit> saveUserProfit(Long userId, Date date);
    Iterable<UserProfit> getUserProfitHistory(long userId, long stockId, Date startDate, Date endDate);

    boolean saveAllUserProfit(Date date);
    Double countTotalAmountOfUserMoney();
}
