package usf.sdlc.controller;

import io.micronaut.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.micronaut.http.annotation.*;
import usf.sdlc.form.StockExtractorResponse;
import usf.sdlc.service.StockExtractorService;

@Controller("/")
public class StockExtractorController {
    private static final Logger LOG = LoggerFactory.getLogger(StockExtractorController.class);

    private final StockExtractorService stockExtractorService;

    public StockExtractorController(StockExtractorService stockExtractorService) {
        this.stockExtractorService = stockExtractorService;
    }

    @Get("/run-extractor")
    public HttpResponse<StockExtractorResponse> runExtractors(/*String[] sym*/) {
        StockExtractorResponse resp = new StockExtractorResponse();
        if (LOG.isDebugEnabled()) {
            LOG.debug("Extracting data for stocks, in total {} ...",""/*sym.length*/);
        }
        resp.setMessage("Success!");
        resp.setStockForms(stockExtractorService.fetchStockQuotes());
        return HttpResponse.created(resp);
    }

    @Get("/ping")
    public String index() {
        return "{\"pong\":true, \"micronaut\": true}";
    }
}

//////
//@Controller("/people")
//;
//
//    Map<String, Person> inMemoryDatastore = new ConcurrentHashMap<>();
//
//    @Post("/saveReactive")
//    public Single<HttpResponse<Person>> save(@Body Single<Person> person) {
//        return person.map(p -> {
//                    inMemoryDatastore.put(p.getFirstName(), p);
//                    return HttpResponse.created(p);
//                }
//        );
//    }
//
//}
///////