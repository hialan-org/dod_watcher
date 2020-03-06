package usf.sdlc.controller;


import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import usf.sdlc.form.StockExtractorResponse;
import usf.sdlc.form.UserProfitResponse;
import usf.sdlc.service.StockExtractorService;

import java.util.Optional;

@Controller("/")
public class UserProfitController {

    private static final Logger LOG = LoggerFactory.getLogger(StockExtractorController.class);

    private final UserProfitController userProfitController;

    public UserProfitController(UserProfitController userProfitController) {
        this.userProfitController = userProfitController;
    }

//    @Get(value = "/user-profit{?userId}")
//    public HttpResponse<UserProfitResponse> runProfitSaveServiceForAUser() {
//        UserProfitResponse resp = new UserProfitResponse();
//
//        return HttpResponse.created(resp);
//    }
}
