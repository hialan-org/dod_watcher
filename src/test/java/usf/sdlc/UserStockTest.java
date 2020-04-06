package usf.sdlc;

import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import usf.sdlc.form.OwnedStockForm;
import usf.sdlc.model.UserProfit;
import usf.sdlc.service.UserProfitService;
import usf.sdlc.service.UserStockService;

import javax.inject.Inject;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
public class UserStockTest {
    @Inject
    UserStockService userStockService;

    @Test
    void testGetStock() {
        List<OwnedStockForm> result = userStockService.findOwnedStock(2202);
        assertEquals(result.size(), 2);
    }

    @Test
    void testGetStockForUserWithoutStock() {
        List<OwnedStockForm> result = userStockService.findOwnedStock(2284);
        assertEquals(result.size(), 0);
    }
}
