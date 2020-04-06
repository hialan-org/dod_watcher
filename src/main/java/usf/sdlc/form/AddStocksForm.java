package usf.sdlc.form;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Introspected;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Introspected
public class AddStocksForm {

    @NotBlank
    @JsonAlias(value = {"stocks, stockActivityForms"})
    @JsonProperty
    private List<StockActivityForm> stocks;

    public AddStocksForm() {
    }

    public AddStocksForm(@NotBlank List<StockActivityForm> stocks) {
        this.stocks = stocks;
    }

    public List<StockActivityForm> getStockActivityForms() {
        return stocks;
    }

    public void setStockActivityForms(List<StockActivityForm> stocks) {
        this.stocks = stocks;
    }

    @Override
    public String toString() {
        return "AddStocksForm{" +
                "stocks=" + stocks +
                '}';
    }
}