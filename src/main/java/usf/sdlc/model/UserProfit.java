package usf.sdlc.model;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "user_profit")
public class UserProfit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private long id;

    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "stock_id", nullable = true)
    private long stockId;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "invested_amount", nullable = false)
    private double investedAmount;

    @Column(name = "user_profit", nullable = false)
    private double userProfit;

    @Column(name = "total_amount", nullable = false)
    private double totalAmount;

    @Column(name = "snp500_profit", nullable = false)
    private double snp500Profit;


    public UserProfit() {
    }

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getInvestedAmount() {
        return investedAmount;
    }

    public void setInvestedAmount(double investedAmount) {
        this.investedAmount = investedAmount;
    }

    public double getUserProfit() {
        return userProfit;
    }

    public void setUserProfit(double userProfit) {
        this.userProfit = userProfit;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getSnp500Profit() {
        return snp500Profit;
    }

    public void setSnp500Profit(double snp500Profit) {
        this.snp500Profit = snp500Profit;
    }
}
