package usf.sdlc;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import usf.sdlc.controller.UserProfitController;
import usf.sdlc.dao.UserProfitRepository;
import usf.sdlc.form.UserProfitResponse;
import usf.sdlc.model.UserProfit;
import usf.sdlc.service.UserProfitServiceImpl;
import usf.sdlc.utils.GetSqlDate;

import javax.inject.Inject;
import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@MicronautTest
public class UserProfitTest {

    @Inject
    UserProfitServiceImpl userProfitServiceImpl;

    @Inject
    UserProfitController userProfitController;

    @Inject
    UserProfitRepository userProfitRepository;

    @Inject
    @Client("/")
    HttpClient client;

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
    void getUserProfitHistoryTest() {
        System.out.println("getUserProfitHistoryTest");
        String uri = "/user-profit/history/0?startDateStr=03-06-2020&endDateStr=03-18-2020";
        MutableHttpRequest<Object> request = HttpRequest.GET(uri).bearerAuth("ya29.a0Ae4lvC007fJTLog5SSi0odFYodQ-RRvs_k3qcQZeOQ4Tumoer1YY1ztn2arZF1ZppDbmOUqsDgcL5hPxH3e3QTm9HBdkNqED5A6hIiGbecNryqeV30B_L9oVJM2P9IhhdoRXGxVvXBtpTShM49ZMgp2hPFD7pmwcmYM");
        String body = client.toBlocking().retrieve(request);
        assertTrue(body.contains("Success"));

//        HttpResponse<UserProfitResponse> r =  userProfitController.getUserProfitHistory("xx 1",0,"03-06-2020", "03-18-2020");
//        assertEquals(body., "Success");

    }





}