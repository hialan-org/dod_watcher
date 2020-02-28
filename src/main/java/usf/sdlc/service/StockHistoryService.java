package usf.sdlc.service;

import usf.sdlc.model.StockHistory;

import java.util.Date;
import java.util.List;

public interface StockHistoryService {
    List<StockHistory> getStockHistoryByDate(Date date);
}
