package usf.sdlc.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.core.annotation.Introspected;

import javax.validation.constraints.NotBlank;

@Introspected
public class StockActivityForm {
    @NotBlank
    private long stockId;
    @NotBlank
    private int stockQuantity;
    @NotBlank
    private double stockPrice;
    @NotBlank
    @JsonProperty
    private boolean isBuy;

    public StockActivityForm() {
    }

    public StockActivityForm(long stockId, int stockQuantity, double stockPrice) {
        this.stockId = stockId;
        this.stockQuantity = stockQuantity;
        this.stockPrice = stockPrice;
    }

    public StockActivityForm(long stockId, int stockQuantity, double stockPrice, boolean isBuy) {
        this.stockId = stockId;
        this.stockQuantity = stockQuantity;
        this.stockPrice = stockPrice;
        this.isBuy = isBuy;
    }

    public long getStockId() {
        return stockId;
    }

    public void setStockId(long stockId) {
        this.stockId = stockId;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public double getStockPrice() {
        return stockPrice;
    }

    public void setStockPrice(double stockPrice) {
        this.stockPrice = stockPrice;
    }

    public boolean isBuy() {
        return isBuy;
    }

    public void setBuy(boolean buy) {
        isBuy = buy;
    }

    @Override
    public String toString() {
        return "StockActivityForm{" +
                "stockId=" + stockId +
                ", stockQuantity=" + stockQuantity +
                ", stockPrice=" + stockPrice +
                ", isBuy=" + isBuy +
                '}';
    }
}
