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
            userService.deleteByUserId(userId);
        } catch (Exception e){
            return HttpResponse.notFound();
        }
        return HttpResponse.ok();
    }

    @Post("/{userId}/addStock")
    public HttpResponse addStock(Long userId, @Body @Valid AddStocksForm stocks) {

        System.out.println("UserController.addStock is triggered.");

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
