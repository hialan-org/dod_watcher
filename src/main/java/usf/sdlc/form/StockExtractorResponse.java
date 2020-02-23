package usf.sdlc.form;

import io.micronaut.core.annotation.Introspected;
import usf.sdlc.model.StockHistory;

import java.util.List;

@Introspected
public class StockExtractorResponse {

    private String message;
    private List<StockHistory> stocksHistory;

    public StockExtractorResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<StockHistory> getStocksHistory() {
        return stocksHistory;
    }

    public void setStocksHistory(List<StockHistory> stocksHistory) {
        this.stocksHistory = stocksHistory;
    }
}