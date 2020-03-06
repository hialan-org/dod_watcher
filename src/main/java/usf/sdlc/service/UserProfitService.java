package usf.sdlc.service;


import usf.sdlc.model.UserProfit;

import java.util.Date;

public interface UserProfitService {
    Iterable<UserProfit> saveUserProfit(Long userId, Date date);
}
