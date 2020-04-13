package usf.sdlc;

import io.micronaut.http.HttpResponse;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import usf.sdlc.controller.UserProfitController;
import usf.sdlc.form.UserProfitResponse;
import usf.sdlc.model.StockHistory;
import usf.sdlc.model.UserProfit;
import usf.sdlc.model.UserStock;
import usf.sdlc.service.UserProfitServiceImpl;

import javax.inject.Inject;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
public class UserProfitTest {

    @Inject
    UserProfitServiceImpl userProfitServiceImpl;

    @Inject
    UserProfitController userProfitController;

    @Test
    void saveUserProfitWithDateTest() {
        System.out.println("saveUserProfitWithDateTest");
        Iterable<UserProfit> done = userProfitServiceImpl.saveUserProfit((long) 1, Date.valueOf("2020-03-31"));
        assertTrue(done.iterator().hasNext());
    }

    @Test
    void saveUserProfitWithNullTest() {
        System.out.println("saveUserProfitWithNullTest");
        Iterable<UserProfit> done = userProfitServiceImpl.saveUserProfit((long) 1,null);
        assertTrue(done.iterator().hasNext());
    }

    @Test
    void saveAllUserProfitWithDateTest() {
        System.out.println("saveAllUserProfitWithDateTest");
        boolean done = userProfitServiceImpl.saveAllUserProfit(Date.valueOf("2020-03-31"));
        assertTrue(done);
    }

    @Test
    void saveAllUserProfitWithNullTest() {
        System.out.println("saveAllUserProfitWithNullTest");
        boolean done = userProfitServiceImpl.saveAllUserProfit(null);
        assertTrue(done);
    }

    @Test
    void runProfitSaveServiceForAUserTest() {
        System.out.println("runProfitSaveServiceForAUserTest");
        HttpResponse<UserProfitResponse> r =  userProfitController.runProfitSaveServiceForAUser((long) 1);
        assertEquals(r.getStatus().getCode(), 201);
    }



}