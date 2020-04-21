package usf.sdlc.filter;


import io.micronaut.core.async.publisher.Publishers;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Filter;
import io.micronaut.http.filter.HttpServerFilter;
import io.micronaut.http.filter.ServerFilterChain;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import org.reactivestreams.Publisher;
import usf.sdlc.config.Constant;
import usf.sdlc.service.UserService;
import javax.inject.Inject;
import javax.security.auth.login.Configuration;
import java.net.URI;
import java.util.Optional;

@Filter("/**")
public class MySecurityFilter implements HttpServerFilter {
    @Inject
    UserService userService;

    @Override
    public Publisher<MutableHttpResponse<?>> doFilter(HttpRequest<?> request, ServerFilterChain chain) {
        URI uri = request.getUri();
        System.out.println("MySecurityFilter.Filter is called for uri: "+uri);
        Publisher<MutableHttpResponse<?>> resp = checkAuthorization(request,new String[]{Constant.ROLE_ADMIN, Constant.ROLE_USER})
                .switchMap((authResult) -> { // authResult - is you result from SecurityService
                    if (!authResult) {
                        return Publishers.just(HttpResponse.unauthorized()); // reject request
                        //return Publishers.just(HttpResponse.status(HttpStatus.FORBIDDEN)); // reject request
                    } else {
                        return chain.proceed(request); // process request as usual
                    }
                })
                .doOnNext(res -> {
                    System.out.println("MySecurityFilter.Responding!");
                });

        return(resp);
    }


    Flowable<Boolean> checkAuthorization(HttpRequest<?> request,String[] roles)
    {
        Flowable<Boolean> flow = Flowable.fromCallable(()->{
            URI uri = request.getUri();
            if(uri.toString().endsWith("loginWithGoogle")){
                System.out.println("No need to check Authorization. By passing it!");
                return true;
            }
            if(uri.toString().endsWith("run-extractor")){
                System.out.println("No need to check Authorization. By passing it!");
                return true;
            }
            if(uri.toString().endsWith("user-profit/all")){
                System.out.println("No need to check Authorization. By passing it!");
                return true;
            }
            System.out.println("Security Engaged!");
            Optional<String> authorization =request.getHeaders().getAuthorization();
            if(authorization.isPresent()){
//                System.out.println("authorization PRESENT:" + authorization.get());
                if(!userService.authorizeUser(authorization.get(), roles)){
                    System.out.println("Authorization is FAILED!");
                    return false;
                }else{
                    System.err.println("Authorization OK!");
                    return true;
                }
            }else{
                System.err.println("Authorization Header NOT PRESENT! Authorization is FAILED! ");
                return false;
            }
        }).subscribeOn(Schedulers.io());
        return(flow);
    }
/*
    @Override
    public Publisher<? extends HttpResponse<?>> doFilter(HttpRequest<?> request, FilterChain chain) {

        URI uri = request.getUri();
        System.out.println("alper. my Filter is called for uri: "+uri);
        Optional<String> authorization =request.getHeaders().getAuthorization();

        if(authorization.isPresent()){
            System.out.println("authorization PRESENT:" + authorization.get());
        }else{
            System.out.println("authorization NOT PRESENT:");
        }



        return chain.proceed(request);
    }

 */
}
