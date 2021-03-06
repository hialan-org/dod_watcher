package usf.sdlc.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import usf.sdlc.config.Constant;
import usf.sdlc.service.UserProfitService;
import usf.sdlc.service.UserService;
import usf.sdlc.service.UserStockActivityService;
import usf.sdlc.service.UserStockService;
import usf.sdlc.utils.Utils;

import javax.inject.Inject;
import java.util.Date;

@Controller("/statistics")
public class StatisticsController {
    private Logger log = LoggerFactory.getLogger(StatisticsController.class);

    @Inject
    UserService userService;

    @Inject
    UserStockService userStockService;

    @Inject
    UserProfitService userProfitService;

    @Inject
    UserStockActivityService userStockActivityService;

    @Inject
    Utils utils;

    public StatisticsController(){
    }

    @Get("/countTotalUserStockNumber")
    public HttpResponse countTotalUserStockQuantity(@Header String Authorization) {
        log.trace("StatisticsController.countTotalUserStockQuantity is triggered.");
        if(!userService.authorizeUser(Authorization, new String[]{Constant.ROLE_ADMIN})){
            log.trace("User doesn't have permission to countTotalUserStockQuantity.");
            return HttpResponse.unauthorized();
        };
        Long totalUserStockNumber = userStockService.countTotalUserStockQuantity();
        //return HttpResponse.ok("{\"count\":"+totalUserStockNumber+"}");
        return HttpResponse.ok(totalUserStockNumber);
    }

    @Get("/totalAmountOfUserMoney")
    public HttpResponse getTotalAmountOfUserMoney(@Header String Authorization) {
        log.trace("StatisticsController.getTotalAmountOfUserMoney is triggered.");
        if(!userService.authorizeUser(Authorization, new String[]{Constant.ROLE_ADMIN})){
            log.trace("User doesn't have permission to getTotalAmountOfUserMoney.");
            return HttpResponse.unauthorized();
        };
        Double totalAmountOfUserMoney = userProfitService.countTotalAmountOfUserMoney();
        //return HttpResponse.ok("{\"total\":"+totalAmountOfUserMoney+"}");
        return HttpResponse.ok(totalAmountOfUserMoney);
    }

    @Get("/getLatestActivityTime")
    public HttpResponse getLatestActivityTime(@Header String Authorization) {
        log.trace("StatisticsController.getLatestActivityTime is triggered.");
        if(!userService.authorizeUser(Authorization, new String[]{Constant.ROLE_ADMIN})){
            log.trace("User doesn't have permission to getLatestActivityTime.");
            return HttpResponse.unauthorized();
        };
        Date latestStockActivityTime = userStockActivityService.getLatestStockActivityTime();

        //return HttpResponse.ok("{\"latestStockActivityTime\":"+latestStockActivityTime+"}");
        return HttpResponse.ok(latestStockActivityTime);
    }

    @Get("/")
    public HttpResponse index(){
        log.info("Index");
        return HttpResponse.ok();
    }



}
