package usf.sdlc.form;

import io.micronaut.core.annotation.Introspected;

import java.util.List;

@Introspected
public class StockExtractorResponse {

    private String message;
    private List<StockForm> stockForms;

    public StockExtractorResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<StockForm> getStockForms() {
        return stockForms;
    }

    public void setStockForms(List<StockForm> stockForms) {
        this.stockForms = stockForms;
    }
}