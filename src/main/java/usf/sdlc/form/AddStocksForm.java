package usf.sdlc.form;

import io.micronaut.core.annotation.Introspected;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Introspected
public class AddStocksForm {

    @NotBlank
    private List<StockActivityForm> stockActivityForms;

    public AddStocksForm() {
    }

    public AddStocksForm(@NotBlank List<StockActivityForm> stockActivityForms) {
        this.stockActivityForms = stockActivityForms;
    }

    public List<StockActivityForm> getStockActivityForms() {
        return stockActivityForms;
    }

    public void setStockActivityForms(List<StockActivityForm> stockActivityForms) {
        this.stockActivityForms = stockActivityForms;
    }
}