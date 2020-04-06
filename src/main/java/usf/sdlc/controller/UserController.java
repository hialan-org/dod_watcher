package usf.sdlc.controller;

import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import usf.sdlc.config.Constant;
import usf.sdlc.form.OwnedStockForm;
import usf.sdlc.service.UserStockService;
import usf.sdlc.utils.Utils;
import usf.sdlc.form.AddStocksForm;
import usf.sdlc.form.Pagination;
import usf.sdlc.form.UserUpdateForm;
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

        System.out.println("UserController.show is triggered.");

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

        System.out.println("UserController.update is triggered.");

        if(!userService.authorizeUser(Authorization, new String[]{Constant.ROLE_ADMIN})){
            System.out.println("User doesn't have permission to update user.");
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

        System.out.println("UserController.list is triggered.");

        if(!userService.authorizeUser(Authorization, new String[]{Constant.ROLE_ADMIN})){
            System.out.println("User doesn't have permission to list users.");
            return HttpResponse.unauthorized();
        };
        return HttpResponse.ok(userService.list(email, args.getPage(), args.getMax()));
    }

    @Delete("/{userId}") //TODO: Set authorization
    public HttpResponse delete(@Header String Authorization,Long userId) {
        System.out.println("UserController.delete is triggered.");
        if(!userService.authorizeUser(Authorization, new String[]{Constant.ROLE_ADMIN})){
            System.out.println("User doesn't have permission to delete user.");
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
    public HttpResponse addStock(@Header String Authorization, @Body @Valid AddStocksForm stocks) {

        System.out.println("UserController.addStock is triggered.");
        String accessToken = Authorization.split(" ")[1];
        User user = userService.findByAccessToken(accessToken);
        System.out.println(stocks.toString());
        userStockActivityService.saveAll(user.getUserId(), stocks);

        System.out.println("UserController.addStock is finished.");
        return HttpResponse.ok();
    }

    @Get("/stocks")
    public List<OwnedStockForm> findOwnedStocks(@Header String Authorization){
        long startTime = System.currentTimeMillis();
        System.out.println("UserController.findOwnedStocks: triggered.");
        String accessToken = Authorization.split(" ")[1];
        User user = userService.findByAccessToken(accessToken);
        List<OwnedStockForm> result = userStockService.findOwnedStock(user.getUserId());

        long endTime = System.currentTimeMillis();
        System.out.printf("UserController.findOwnedStocks: finished in %dms\n", endTime-startTime);

        return result;
    }

    protected URI location(Long userId) {
        return URI.create("/users/" + userId);
    }

    protected URI location(User user) {
        return location(user.getUserId());
    }
}
