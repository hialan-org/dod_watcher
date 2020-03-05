package usf.sdlc.service;


import java.util.Date;

public interface UserProfitService {
    float getUserProfitByDate(Long userId, Date date);
}
