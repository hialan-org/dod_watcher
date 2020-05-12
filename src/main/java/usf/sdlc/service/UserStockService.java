package usf.sdlc.service;

import usf.sdlc.form.OwnedStockForm;

import java.util.List;

public interface UserStockService {
    List<OwnedStockForm> findOwnedStock(long userId);
    Long countTotalUserStockNumber();
}
