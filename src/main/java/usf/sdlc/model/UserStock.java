package usf.sdlc.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "user_stock")
public class UserStock {
    public UserStock() {
    }

    public UserStock(UserStockId userStockId, float stockAveragePrice, int stockQuantity, Timestamp updatedDate, int isOwned) {
        this.userStockId = userStockId;
        this.stockAveragePrice = stockAveragePrice;
        this.stockQuantity = stockQuantity;
        this.updatedDate = updatedDate;
        this.isOwned = isOwned;
    }

    public UserStock(UserStockId userStockId, float stockAveragePrice, int stockQuantity, int isOwned) {
        this.userStockId = userStockId;
        this.stockAveragePrice = stockAveragePrice;
        this.stockQuantity = stockQuantity;
        this.isOwned = isOwned;
    }

    @EmbeddedId
    private UserStockId userStockId;

//    @Id
//    @Column(name = "user_id", nullable = false)
//    private long userId;
//
//    @Id
//    @Column(name = "stock_id", nullable = false)
//    private long stockId;

    @Column(name = "stock_average_price", nullable = false)
    private float stockAveragePrice;

    @Column(name = "stock_quantity", nullable = false)
    private int stockQuantity;

    @Column(name = "updated_date", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp updatedDate;

    @Column(name = "is_owned")
    private int isOwned;

    public UserStockId getUserStockId() {
        return userStockId;
    }

    public void setUserStockId(UserStockId userStockId) {
        this.userStockId = userStockId;
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

    public Timestamp getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Timestamp updatedDate) {
        this.updatedDate = updatedDate;
    }

    public int getIsOwned() {
        return isOwned;
    }

    public void setIsOwned(int isOwned) {
        this.isOwned = isOwned;
    }

    @Override
    public String toString() {
        return "userStockId->"+this.userStockId.toString()
                +" , stockAveragePrice:"+this.stockAveragePrice
                +", stockQuantity:"+this.stockQuantity
                +", this.updatedDate:"+this.updatedDate
                +", isOwned:"+this.isOwned;
    }
}
