package usf.sdlc.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import usf.sdlc.form.StockCreateForm;
import usf.sdlc.form.StockHistoryForm;
import usf.sdlc.model.Stock;
import usf.sdlc.model.StockHistory;
import usf.sdlc.service.StockHistoryService;
import usf.sdlc.service.StockService;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller("/stocks")
public class StockController {
    Logger logger = LoggerFactory.getLogger(StockController.class);

    @Inject
    StockService stockService;

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

        logger.info("StockController.getTopYield is triggered.");
        logger.info("StockController.getTopYield: /stocks/getTopYield?date={}&range={}\n", dateStr, range);
        Date date = null;
        try {
            date = new SimpleDateFormat("MM-dd-yyyy").parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<StockHistoryForm> stockHistoryList = new ArrayList<>();
        stockHistoryList = stockHistoryService.getTopYieldStockByDate(date, range);
        logger.info("StockController.getTopYield is finished.");

        return stockHistoryList;
    }

    protected URI location(Long stockId) {
        return URI.create("/stocks/" + stockId);
    }

    protected URI location(Stock stock) {
        return location(stock.getStockId());
    }
}
