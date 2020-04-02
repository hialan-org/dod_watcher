package usf.sdlc.service;

import usf.sdlc.form.StockHistoryForm;
import usf.sdlc.model.StockHistory;

import java.util.Date;
import java.util.List;

public interface StockHistoryService {
    List<StockHistoryForm> getTopYieldStockByDate(Date date, int numOfResult);
}
