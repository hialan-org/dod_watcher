package usf.sdlc.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import usf.sdlc.dao.SortingAndOrderArguments;
import usf.sdlc.dao.StockRepository;
import usf.sdlc.form.StockCreateForm;
import usf.sdlc.form.UserCreateForm;
import usf.sdlc.model.Stock;
import usf.sdlc.model.StockHistory;
import usf.sdlc.model.User;
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
    @Inject
    StockService stockService;

    StockHistoryService stockHistoryService;

    public StockController(StockHistoryService stockHistoryService) {
        this.stockHistoryService = stockHistoryService;
    }

    @Get("/")
    public List<Stock> list() {
        return stockService.list();
    }

    @Post("/add")
    public HttpResponse<Stock> save(@Body @Valid StockCreateForm stockDTO) {
        Stock stock = stockService.save(stockDTO);

        return HttpResponse
                .created(stock)
                .headers(headers -> headers.location(location( stock.getStockId())));
    }

//    @Get("/dogOfTheDow/{dateStr}")
    @Get("/dogOfTheDow")
    public List<StockHistory> getDogOfTheDow(@QueryValue String dateStr, @Header String Authorization) {
        System.out.println(Authorization);
        System.out.println(dateStr);
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        List<StockHistory> stockHistoryList = new ArrayList<>();
        stockHistoryList = stockHistoryService.getStockHistoryByDate(date);

        return stockHistoryList;
    }

    protected URI location(Long stockId) {
        return URI.create("/stocks/" + stockId);
    }

    protected URI location(Stock stock) {
        return location(stock.getStockId());
    }
}
