package usf.sdlc.form;

import usf.sdlc.model.StockHistory;

import javax.persistence.Column;

public class StockHistoryForm {
    private long stockId;
    private String symbol;
    private String companyName;
    private float latestPrice;
    private float dividendYield;

    public StockHistoryForm() {
    }

    public StockHistoryForm(long stockId, String symbol, String companyName, float latestPrice, float dividendYield) {
        this.stockId = stockId;
        this.symbol = symbol;
        this.companyName = companyName;
        this.latestPrice = latestPrice;
        this.dividendYield = dividendYield;
    }

    public StockHistoryForm(StockHistory stockHistory) {
        this.stockId = stockHistory.getStockId();
        this.symbol = stockHistory.getStock().getSymbol();
        this.companyName = stockHistory.getStock().getCompanyName();
        this.latestPrice = stockHistory.getLatestPrice();
        this.dividendYield = stockHistory.getDividendYield();
    }

    public long getStockId() {
        return stockId;
    }

    public void setStockId(long stockId) {
        this.stockId = stockId;
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

    public float getLatestPrice() {
        return latestPrice;
    }

    public void setLatestPrice(float latestPrice) {
        this.latestPrice = latestPrice;
    }

    public float getDividendYield() {
        return dividendYield;
    }

    public void setDividendYield(float dividendYield) {
        this.dividendYield = dividendYield;
    }
}
