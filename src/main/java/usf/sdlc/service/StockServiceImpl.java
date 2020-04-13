package usf.sdlc.service;

import usf.sdlc.dao.StockRepository;
import usf.sdlc.form.StockCreateForm;
import usf.sdlc.model.Stock;

import javax.inject.Inject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class StockServiceImpl  implements StockService{

    @Inject
    StockRepository stockRepository;

    public List<Stock> list() {
        return (List<Stock>) stockRepository.findAll();
    }

    @Override
    public Stock save(StockCreateForm stock) {
        return stockRepository.save(stock);
    }


    public java.sql.Date getSqlDate(String timeStr) {
        String[] timeStrArr = timeStr.split("-");
        if (timeStrArr.length != 3) {
            timeStrArr = new String[]{"January", "1", "1970"};
        }
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(timeStrArr[0]+"-"+timeStrArr[1]+"-"+timeStrArr[2]);
        } catch (ParseException e) {
            System.out.println("Parse Exception in getTimeStamp func, "+ e.getMessage());
            try {
                date = new SimpleDateFormat("yyyy-MM-dd").parse("1970-01-01");
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
        assert date != null;
        return new java.sql.Date(date.getTime());
    }
}
