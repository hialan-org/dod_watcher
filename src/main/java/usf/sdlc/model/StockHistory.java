package usf.sdlc.model;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name="stock_history")
public class StockHistory {

    public StockHistory() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private long id;

//    @Column(name = "stock_id", nullable = false, unique = false)
//    private long stockId;

    @Column(name = "latest_price", nullable = false, unique = false)
    private float latestPrice;

    @Column(name = "dividend_yield", nullable = false, unique = false)
    private float dividendYield;

    @Column(name = "latest_time", nullable = false, unique = false)
    private Date latestTime;

    @ManyToOne
    @JoinColumn(name = "stock_id")
    private Stock stock;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

//    public long getStockId() {
//        return stockId;
//    }
//
//    public void setStockId(long stockId) {
//        this.stockId = stockId;
//    }

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

    public Date getLatestTime() {
        return latestTime;
    }

    public void setLatestTime(Date latestTime) {
        this.latestTime = latestTime;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "StockHistory{" +
                "id=" + id +
//                ", stockId=" + stockId +
                ", latestPrice=" + latestPrice +
                ", dividendYield=" + dividendYield +
                ", latestTime=" + latestTime +
                '}';
    }
}