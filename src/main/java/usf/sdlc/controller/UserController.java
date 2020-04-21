package usf.sdlc.controller;

import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import usf.sdlc.config.Constant;
import usf.sdlc.form.*;
import usf.sdlc.model.UserStock;
import usf.sdlc.service.UserStockService;
import usf.sdlc.utils.Utils;
import usf.sdlc.model.User;
import usf.sdlc.service.UserService;
import usf.sdlc.service.UserStockActivityService;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Controller("/users")
public class UserController {
    private Logger log = LoggerFactory.getLogger(UserController.class);

    @Inject
    UserService userService;
    @Inject
    UserStockActivityService userStockActivityService;
    @Inject
    UserStockService userStockService;
    @Inject
    Utils utils;

    public UserController(){
    }

    @Get("/{userId}") //TODO: Set authorization
    public HttpResponse show(Long userId) {

        log.trace("UserController.show is triggered.");

        User user = userService
                .findByUserId(userId);
        if(user == null){
            return HttpResponse.notFound();
        }
        return HttpResponse
                .ok(user);
    }

    @Put() //TODO: Set authorization
    public HttpResponse update(@Header String Authorization,@Body @Valid UserUpdateForm command) {

        log.trace("UserController.update is triggered.");

        if(!userService.authorizeUser(Authorization, new String[]{Constant.ROLE_ADMIN})){
            log.trace("User doesn't have permission to update user.");
            return HttpResponse.unauthorized();
        };
        User user = userService.findByUserId(command.getUserId());
        if(user!=null){
            user.setEmail(command.getEmail());
        } else {
            return HttpResponse
                    .notFound();
        }
        userService.update(user);

        return HttpResponse
                .ok()
                .header(HttpHeaders.LOCATION, location(command.getUserId()).getPath());
    }

    @Get(value = "{?email,args*}")
    public HttpResponse list(@Header String Authorization,@Valid Pagination args, @QueryValue @Nullable String email) {

        log.trace("UserController.list is triggered.");

        if(!userService.authorizeUser(Authorization, new String[]{Constant.ROLE_ADMIN})){
            log.trace("User doesn't have permission to list users.");
            return HttpResponse.unauthorized();
        };
        return HttpResponse.ok(userService.list(email, args.getPage(), args.getMax()));
    }

    @Delete("/{userId}") //TODO: Set authorization
    public HttpResponse delete(@Header String Authorization,Long userId) {
        log.trace("UserController.delete is triggered.");
        if(!userService.authorizeUser(Authorization, new String[]{Constant.ROLE_ADMIN})){
            log.trace("User doesn't have permission to delete user.");
            return HttpResponse.unauthorized();
        };
        try{
            if(userService.deleteByUserId(userId))
                return HttpResponse.ok();
            else
                return HttpResponse.notFound();
        } catch (Exception e){
            return HttpResponse.notFound();
        }
    }

    @Post("/addStock")
    public List<UserStockForm> addStock(@Header String Authorization, @Body @Valid List<StockActivityForm> stocks) {

        long startTime = System.currentTimeMillis();
        log.trace("UserController.addStock: is triggered.");
        String accessToken = Authorization.split(" ")[1];
        User user = userService.findByAccessToken(accessToken);
        log.trace(stocks.toString());
        List<UserStockForm> updatedStocks = userStockActivityService.saveAll(user.getUserId(), stocks);

        long endTime = System.currentTimeMillis();
        log.trace("UserController.addStock: finished in {}ms\n", endTime-startTime);
        return updatedStocks;
    }

    @Get("/stocks")
    public List<OwnedStockForm> findOwnedStocks(@Header String Authorization){
        long startTime = System.currentTimeMillis();
        log.trace("UserController.findOwnedStocks: triggered.");
        String accessToken = Authorization.split(" ")[1];
        User user = userService.findByAccessToken(accessToken);
        List<OwnedStockForm> result = userStockService.findOwnedStock(user.getUserId());

        long endTime = System.currentTimeMillis();
        log.trace("UserController.findOwnedStocks: finished in {}dms\n", endTime-startTime);

        return result;
    }

    protected URI location(Long userId) {
        return URI.create("/users/" + userId);
    }

    protected URI location(User user) {
        return location(user.getUserId());
    }
}
