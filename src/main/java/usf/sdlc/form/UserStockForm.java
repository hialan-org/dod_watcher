package usf.sdlc.form;

import usf.sdlc.model.StockHistory;
import usf.sdlc.model.UserStock;

public class UserStockForm {
    public UserStockForm() {
    }

    public UserStockForm(UserStock userStock, StockHistory stockHistory) {
        this.userId = userStock.getUserStockId().getUserId();
        this.stockId = userStock.getUserStockId().getStockId();
        this.stockAveragePrice = userStock.getStockAveragePrice();
        this.stockQuantity = userStock.getStockQuantity();
        this.latestPrice = stockHistory.getLatestPrice();
        this.symbol = stockHistory.getStock().getSymbol();
    }

    private long userId;
    private long stockId;
    private float stockAveragePrice;
    private int stockQuantity;
    private float latestPrice;
    private String symbol;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getStockId() {
        return stockId;
    }

    public void setStockId(long stockId) {
        this.stockId = stockId;
    }

    public float getStockAveragePrice() {
        return stockAveragePrice;
    }

    public void setStockAveragePrice(float stockAveragePrice) {
        this.stockAveragePrice = stockAveragePrice;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public float getLatestPrice() {
        return latestPrice;
    }

    public void setLatestPrice(float latestPrice) {
        this.latestPrice = latestPrice;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
