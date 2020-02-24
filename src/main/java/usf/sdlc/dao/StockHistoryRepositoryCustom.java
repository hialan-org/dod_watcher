package usf.sdlc.dao;

import usf.sdlc.model.StockHistory;

import java.sql.Timestamp;
import java.util.List;

public interface StockHistoryRepositoryCustom {
    List<StockHistory> customFindByLatestTime(Timestamp timestamp);
}
