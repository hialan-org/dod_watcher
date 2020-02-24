package usf.sdlc.service;

import usf.sdlc.dao.StockRepository;
import usf.sdlc.model.Stock;

import javax.inject.Inject;
import java.util.List;

public class StockService {

    @Inject
    StockRepository stockRepository;

    public List<Stock> list() {
        return (List<Stock>) stockRepository.findAll();
    }
}
