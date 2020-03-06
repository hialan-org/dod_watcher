package usf.sdlc.controller;


import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import usf.sdlc.form.StockExtractorResponse;
import usf.sdlc.form.UserProfitResponse;
import usf.sdlc.model.UserProfit;
import usf.sdlc.service.StockExtractorService;
import usf.sdlc.service.UserProfitServiceImpl;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller("/")
public class UserProfitController {

    private static final Logger LOG = LoggerFactory.getLogger(StockExtractorController.class);

    @Inject
    UserProfitServiceImpl userProfitServiceImpl;

    public UserProfitController() {
    }

    @Get("/user-profit/{userId}")
    public HttpResponse<UserProfitResponse> runProfitSaveServiceForAUser(Long userId) {
        UserProfitResponse resp = new UserProfitResponse();
        Iterable<UserProfit> rowsToSaveIter = userProfitServiceImpl.saveUserProfit(userId, null);
        List<UserProfit> rowsToSave = new ArrayList<>();
        for(UserProfit us : rowsToSaveIter) {
            rowsToSave.add(us);
        }
        resp.setUserProfits(rowsToSave);
        resp.setMessage("Success");
        return HttpResponse.created(resp);
    }
}
