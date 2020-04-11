package usf.sdlc.form;

public class OwnedStockForm {
    public OwnedStockForm() {
    }

    public OwnedStockForm(long stockId, String symbol, double buyPrice, int quantity, float latestPrice) {
        this.stockId = stockId;
        this.symbol = symbol;
        this.buyPrice = buyPrice;
        this.quantity = quantity;
        this.latestPrice = latestPrice;

        System.out.println("Latest price: " + this.getLatestPrice() + " - " + latestPrice);
    }

    private long stockId;

    private String symbol;

    private double buyPrice;

    private int quantity;

    private float latestPrice;

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

    public float getLatestPrice() {
        return latestPrice;
    }

    public void setLatestPrice(float latestPrice) {
        this.latestPrice = latestPrice;
    }

    public long getStockId() {
        return stockId;
    }

    public void setStockId(long stockId) {
        this.stockId = stockId;
    }
}
