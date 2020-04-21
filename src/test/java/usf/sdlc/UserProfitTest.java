package usf.sdlc;

import io.micronaut.http.HttpResponse;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import usf.sdlc.controller.UserProfitController;
import usf.sdlc.dao.UserProfitRepository;
import usf.sdlc.form.UserProfitResponse;
import usf.sdlc.model.StockHistory;
import usf.sdlc.model.UserProfit;
import usf.sdlc.model.UserStock;
import usf.sdlc.service.UserProfitServiceImpl;
import usf.sdlc.utils.GetSqlDate;

import javax.inject.Inject;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
public class UserProfitTest {

    @Inject
    UserProfitServiceImpl userProfitServiceImpl;

    @Inject
    UserProfitController userProfitController;

    @Inject
    UserProfitRepository userProfitRepository;

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

    @Test
    void customGetUserProfitHistoryTest() {
        long userId = 1;
        long stockId = 0;
        Date sd = GetSqlDate.getSqlDate("03-06-2020");
        Date ed = GetSqlDate.getSqlDate("03-18-2020");

        List<UserProfit> ans = userProfitRepository.customGetUserProfitHistory(userId, stockId, sd, ed);
        assertTrue(ans.size()>0);

    }

    @Test
    void getUserProfitHistory() {
        System.out.println("getUserProfitHistoryTest");
        HttpResponse<UserProfitResponse> r =  userProfitController.getUserProfitHistory(1,0,"03-06-2020", "03-18-2020");
        assertEquals(Objects.requireNonNull(r.body()).getMessage(), "Success");

    }





}