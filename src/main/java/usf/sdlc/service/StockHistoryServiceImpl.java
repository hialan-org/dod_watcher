package usf.sdlc.service;

import usf.sdlc.dao.StockHistoryRepository;
import usf.sdlc.model.StockHistory;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class StockHistoryServiceImpl implements StockHistoryService {

    @Inject
    StockHistoryRepository stockHistoryRepository;

    @Override
    public List<StockHistory> getStockHistoryByDate(Date date) {
        Timestamp timestamp = new Timestamp(date.getTime());
        System.out.println(date + " : " + timestamp);
        return stockHistoryRepository.customFindByLatestTime(timestamp, 10);
    }
}
