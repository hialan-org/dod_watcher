package usf.sdlc.service;

import usf.sdlc.form.StockCreateForm;
import usf.sdlc.model.Stock;

import java.util.List;

public interface StockService {
    List<Stock> list();
    Stock save(StockCreateForm stock);
}
