package usf.sdlc.model;

import javax.persistence.*;


@Entity
@Table(name="stock")
public class Stock {

    public Stock() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "stockId", nullable = false, unique = true)
    private long stockId;

    @Column(name = "symbol", nullable = false, unique = true)
    private String symbol;

    @Column(name = "companyName", nullable = false, unique = true)
    private String companyName;

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

}
