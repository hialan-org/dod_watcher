package usf.sdlc;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientException;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
public class GoogleControllerTest {

    @Inject
    @Client("/")
    HttpClient client;

    @Test
    void testLoginWithGoogle() {
        System.out.println("testLoginWithGoogle");
        String uri = "loginWithGoogle";
        MutableHttpRequest<Object> request = HttpRequest.POST(uri,"");
        request.bearerAuth("ya29.a0AfH6SMDTuqHw25_ml1y4vTmY_-LitG17W-u7i72Xt4dK9aABdDp5-wgJEOpdsU-q0KbVVgbv1gqC1vhgaFrlThhnZNnejm6-L_xJ8KVTSTzdgoTDtNr3MA6KyVt-3BL8GYK6WTqSI8dkefgReR_DZO2uoObzV6x3xxI");
        //TODO: WHY 400???
        try {
            client.toBlocking().retrieve(request);
        }catch(HttpClientException e){
            System.out.println(e);
            assertEquals(e.getMessage(),"Unauthorized");
        }

    }

}
