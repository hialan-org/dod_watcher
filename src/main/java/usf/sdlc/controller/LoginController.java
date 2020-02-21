package usf.sdlc.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import io.micronaut.views.View;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {

    @Secured(SecurityRule.IS_ANONYMOUS)
    @View("home")
    @Get
    public Map<String, Object> index() {
        return new HashMap<>();
    }

   @Get("/oauth/callback/google")
    public String callback() {

        System.out.println("call back received!!");

        return "Test";
    }

}
