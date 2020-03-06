package usf.sdlc.controller;

import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import usf.sdlc.config.Constant;
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

@Controller("/users")
public class UserController {

    @Inject
    UserService userService;
    @Inject
    UserStockActivityService userStockActivityService;
    @Inject
    Utils utils;

    public UserController(){
    }

    @Get("/{userId}") //TODO: Set authorization
    public HttpResponse show(Long userId) {
        User user = userService
                .findByUserId(userId);
        if(user == null){
            return HttpResponse.notFound();
        }
        return HttpResponse
                .ok(user);
    }

    @Put() //TODO: Set authorization
    public HttpResponse update(@Body @Valid UserUpdateForm command) {
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
    public HttpResponse list(@Header String Authorization, @Valid Pagination args, @QueryValue @Nullable String email) {
        if(!utils.authorization(Authorization, new String[]{Constant.ROLE_ADMIN})){
            return HttpResponse.unauthorized();
        };
        return HttpResponse.ok(userService.list(email, args.getPage(), args.getMax()));
    }

    @Delete("/{userId}") //TODO: Set authorization
    public HttpResponse delete(Long userId) {
        try{
            userService.deleteByUserId(userId);
        } catch (Exception e){
            return HttpResponse.notFound();
        }
        return HttpResponse.ok();
    }

    @Post("/{userId}/addStock") //TODO: Set authorization
    public HttpResponse addStock(Long userId, @Body @Valid AddStocksForm stocks) {
        System.out.println(stocks);
        userStockActivityService.saveAll(userId, stocks);
        return HttpResponse.ok();
    }

    protected URI location(Long userId) {
        return URI.create("/users/" + userId);
    }

    protected URI location(User user) {
        return location(user.getUserId());
    }
}
