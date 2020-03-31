package usf.sdlc.service;

import usf.sdlc.dao.StockHistoryRepository;
import usf.sdlc.form.StockHistoryForm;
import usf.sdlc.model.StockHistory;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StockHistoryServiceImpl implements StockHistoryService {

    @Inject
    StockHistoryRepository stockHistoryRepository;

    @Override
    public List<StockHistoryForm> getStockHistoryByDate(Date date) {
        Timestamp timestamp = new Timestamp(date.getTime());
        System.out.println(date + " : " + timestamp);
        List<StockHistoryForm> result = new ArrayList<>();
        List<StockHistory> stockHistories = stockHistoryRepository.customFindTopYieldByLatestTime(timestamp, 10);
        for(int i=0;i<stockHistories.size();i++){
//            System.out.println(stockHistories.get(i).getStock().getSymbol());
            result.add(new StockHistoryForm(stockHistories.get(i)));
        }
        return result;
    }
}
