package usf.sdlc.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.Post;
import usf.sdlc.model.User;
import usf.sdlc.service.UserService;

import javax.inject.Inject;

@Controller()
public class GoogleController {
    @Inject
    UserService userService;

    /**
     *
     * @param Authorization Bearer with accessToken
     * @return
     */
    @Post("/loginWithGoogle")
    public HttpResponse loginWithGoogle(@Header String Authorization) {
        System.out.println(Authorization);
        String accessToken = Authorization.split(" ")[1];
        User user = userService.loginWithGoogle(accessToken);
        if(user==null){
            return HttpResponse.unauthorized();
        }
        return HttpResponse.ok();
    }
}
