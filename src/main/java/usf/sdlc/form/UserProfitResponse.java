package usf.sdlc.form;

import io.micronaut.core.annotation.Introspected;
import usf.sdlc.model.StockHistory;
import usf.sdlc.model.UserProfit;

import java.util.List;

@Introspected
public class UserProfitResponse {

    private String message;
    private Iterable<UserProfit> userProfits;

    public UserProfitResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Iterable<UserProfit> getUserProfits() {
        return userProfits;
    }

    public void setUserProfits(Iterable<UserProfit> userProfits) {
        this.userProfits = userProfits;
    }
}
