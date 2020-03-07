package usf.sdlc.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class UserStockId implements Serializable {
    public UserStockId() {
    }

    public UserStockId(long userId, long stockId) {
        this.userId = userId;
        this.stockId = stockId;
    }

    @Column(name = "user_id")
    private long userId;

    @Column(name = "stock_id")
    private long stockId;

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

    public String toString() {
        return "userId:"+ this.userId+ ", stockId:"+ this.stockId;
    }
}
