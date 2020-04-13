package usf.sdlc.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import usf.sdlc.dao.StockHistoryRepository;
import usf.sdlc.form.StockCreateForm;
import usf.sdlc.form.StockExtractorResponse;
import usf.sdlc.form.StockHistoryForm;
import usf.sdlc.model.Stock;
import usf.sdlc.model.StockHistory;
import usf.sdlc.service.StockHistoryService;
import usf.sdlc.service.StockService;
import usf.sdlc.service.StockServiceImpl;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller("/stocks")
public class StockController {
    @Inject
    StockService stockService;

    @Inject
    StockServiceImpl stockServiceImpl;

    @Inject
    StockHistoryRepository stockHistoryRepository;

    StockHistoryService stockHistoryService;

    public StockController(StockHistoryService stockHistoryService) {
        this.stockHistoryService = stockHistoryService;
    }

    @Get() //TODO: Set authorization
    public List<Stock> list() {
        return stockService.list();
    }

    @Post("/add") //TODO: Set authorization
    public HttpResponse<Stock> save(@Body @Valid StockCreateForm stockDTO) {
        Stock stock = stockService.save(stockDTO);

        return HttpResponse
                .created(stock)
                .headers(headers -> headers.location(location( stock.getStockId())));
    }

//    @Get("/dogOfTheDow/{dateStr}")
    @Get("/getTopYield") //TODO: Set authorization
    public List<StockHistoryForm> getTopYield(@QueryValue("date") String dateStr, @QueryValue int range, @Header String Authorization) {

        System.out.println("StockController.getTopYield: triggered.");
        System.out.printf("StockController.getTopYield: /stocks/getTopYield?date=%s&range=%d\n", dateStr, range);
        Date date = null;
        try {
            date = new SimpleDateFormat("MM-dd-yyyy").parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<StockHistoryForm> stockHistoryList = new ArrayList<>();
        stockHistoryList = stockHistoryService.getTopYieldStockByDate(date, range);
        System.out.println("StockController.getTopYield: finished.");

        return stockHistoryList;
    }

    @Get("/stock-history/{dateStr}")
    public HttpResponse<StockExtractorResponse> getStockHistoryForDate(String dateStr) {
        StockExtractorResponse resp = new StockExtractorResponse();
        resp.setMessage("Success!");
        Date d = stockServiceImpl.getSqlDate(dateStr);
        resp.setStocksHistory(stockHistoryRepository.customFindStocksHistoryOnDate(d));
        return HttpResponse.created(resp);
    }

    protected URI location(Long stockId) {
        return URI.create("/stocks/" + stockId);
    }

    protected URI location(Stock stock) {
        return location(stock.getStockId());
    }
}
