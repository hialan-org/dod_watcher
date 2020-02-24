package usf.sdlc.service;

import usf.sdlc.form.AddStocksForm;
import usf.sdlc.model.UserStockActivity;

import java.util.List;

public interface UserStockActivityService {
    void saveAll(long userId, AddStocksForm userStockActivities);
}
