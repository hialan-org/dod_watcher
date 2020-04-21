package usf.sdlc.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="user_stock_activity")
public class UserStockActivity {

    public UserStockActivity() {
    }

    public UserStockActivity(long userId, long stockId, int stockQuantity, float stockPrice, Timestamp buyDate, int active) {
        this.userId = userId;
        this.stockId = stockId;
        this.stockQuantity = stockQuantity;
        this.stockPrice = stockPrice;
        this.buyDate = buyDate;
        this.active = active;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private long id;

    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "stock_id", nullable = false)
    private long stockId;

    @Column(name = "stock_quantity", nullable = false)
    private int stockQuantity;

    @Column(name = "stock_price", nullable = false)
    private float stockPrice;

    @Column(name = "buy_date")
    private Timestamp buyDate;

    @Column(name = "sell_date")
    private Timestamp sellDate;

    @Column(name = "active")
    private int active;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public float getStockPrice() {
        return stockPrice;
    }

    public void setStockPrice(float stockPrice) {
        this.stockPrice = stockPrice;
    }

    public Timestamp getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(Timestamp buyDate) {
        this.buyDate = buyDate;
    }

    public Timestamp getSellDate() {
        return sellDate;
    }

    public void setSellDate(Timestamp sellDate) {
        this.sellDate = sellDate;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }
}
