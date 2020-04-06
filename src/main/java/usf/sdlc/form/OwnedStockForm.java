package usf.sdlc.form;

public class OwnedStockForm {
    public OwnedStockForm() {
    }

    public OwnedStockForm(String symbol, double buyPrice, int quantity, double latestPrice) {
        this.symbol = symbol;
        this.buyPrice = buyPrice;
        this.quantity = quantity;
        this.latestPrice = latestPrice;
    }

    private String symbol;

    private double buyPrice;

    private int quantity;

    private double latestPrice;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getLatestPrice() {
        return latestPrice;
    }

    public void setLatestPrice(double latestPrice) {
        this.latestPrice = latestPrice;
    }
}
