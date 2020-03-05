package usf.sdlc.controller;

import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import usf.sdlc.model.User;
import usf.sdlc.service.UserService;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

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
        Map<CharSequence, CharSequence> headers = new HashMap<>();
        headers.put("Access-Control-Allow-Origin", "*");
        return HttpResponse.ok(user).headers(headers);
//        return HttpResponse.ok(user).header(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
    }


//    @Options("/loginWithGoogle")
//    public HttpResponse option(@Header String Authorization) {
//        Map<CharSequence, CharSequence> headers = new HashMap<>();
//
//        headers.put("Access-Control-Allow-Credentials", "true");
//        headers.put("Access-Control-Allow-Origin", "*");
//
//        return HttpResponse.ok().headers(headers);
//    }
}
