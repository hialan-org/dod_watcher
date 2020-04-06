package usf.sdlc.service;

import usf.sdlc.form.OwnedStockForm;
import usf.sdlc.model.UserStock;

import java.util.List;

public interface UserStockService {
    List<OwnedStockForm> findOwnedStock(long userId);
}
