package usf.sdlc.form;

import io.micronaut.core.annotation.Introspected;
import usf.sdlc.model.Stock;

import javax.validation.constraints.NotBlank;

public class StockCreateForm extends Stock {

    @NotBlank
    private String symbol;

    @NotBlank
    private String companyName;

    public StockCreateForm() {
    }

    public StockCreateForm(@NotBlank String symbol, @NotBlank String companyName) {
        this.symbol = symbol;
        this.companyName = companyName;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
