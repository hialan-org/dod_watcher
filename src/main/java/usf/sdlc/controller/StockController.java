package usf.sdlc.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import usf.sdlc.dao.SortingAndOrderArguments;
import usf.sdlc.dao.StockRepository;
import usf.sdlc.form.StockCreateForm;
import usf.sdlc.form.UserCreateForm;
import usf.sdlc.model.Stock;
import usf.sdlc.model.User;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Controller("/stocks")
public class StockController {
    @Inject
    StockRepository stockRepository;

    @Get("/")
    public List<Stock> list() {
        return (List<Stock>) stockRepository.findAll();
    }

    @Post("/add")
    public HttpResponse<Stock> save(@Body @Valid StockCreateForm stockDTO) {
        Stock stock = stockRepository.save(stockDTO);

        return HttpResponse
                .created(stock)
                .headers(headers -> headers.location(location( stock.getStockId())));
    }

    protected URI location(Long stockId) {
        return URI.create("/stocks/" + stockId);
    }

    protected URI location(Stock stock) {
        return location(stock.getStockId());
    }
}
