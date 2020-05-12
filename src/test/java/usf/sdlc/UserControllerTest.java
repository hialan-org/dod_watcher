package usf.sdlc;

import com.google.gson.Gson;
import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientException;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import usf.sdlc.form.UserResponse;

import javax.inject.Inject;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@MicronautTest
public class UserControllerTest {

    @Inject
    @Client("/")
    HttpClient client;

    @Test
    void testShow() {
        System.out.println("getUserProfitHistoryTest");
        String uri = "users/1";
        MutableHttpRequest<Object> request = HttpRequest.GET(uri).bearerAuth("ya29.a0Ae4lvC007fJTLog5SSi0odFYodQ-RRvs_k3qcQZeOQ4Tumoer1YY1ztn2arZF1ZppDbmOUqsDgcL5hPxH3e3QTm9HBdkNqED5A6hIiGbecNryqeV30B_L9oVJM2P9IhhdoRXGxVvXBtpTShM49ZMgp2hPFD7pmwcmYM");
        String body = client.toBlocking().retrieve(request);
        //System.out.println(body);
        Gson gson = new Gson();
        UserResponse userResponse = gson.fromJson(body, UserResponse.class);
        assertEquals("alper.ozdamar@gmail.com",userResponse.getEmail());
    }

    @Test
    void testDelete() {
        System.out.println("testDelete");
        String uri = "users/-2";
        MutableHttpRequest<Object> request = HttpRequest.DELETE(uri).bearerAuth("ya29.a0Ae4lvC007fJTLog5SSi0odFYodQ-RRvs_k3qcQZeOQ4Tumoer1YY1ztn2arZF1ZppDbmOUqsDgcL5hPxH3e3QTm9HBdkNqED5A6hIiGbecNryqeV30B_L9oVJM2P9IhhdoRXGxVvXBtpTShM49ZMgp2hPFD7pmwcmYM");
        try {
            client.toBlocking().retrieve(request);
        }catch(HttpClientException e){
            System.out.println(e);
            assertEquals(e.getMessage(),"Not Found");
        }
    }

    @Test
    public void testFindOwnedStocks(){
        System.out.println("testFindOwnedStocks");
        String uri = "users/stocks";
        MutableHttpRequest<Object> request = HttpRequest.GET(uri).bearerAuth("ya29.a0Ae4lvC007fJTLog5SSi0odFYodQ-RRvs_k3qcQZeOQ4Tumoer1YY1ztn2arZF1ZppDbmOUqsDgcL5hPxH3e3QTm9HBdkNqED5A6hIiGbecNryqeV30B_L9oVJM2P9IhhdoRXGxVvXBtpTShM49ZMgp2hPFD7pmwcmYM");
        String body = client.toBlocking().retrieve(request);
        //System.out.println(body);
        Gson gson = new Gson();
        List list = gson.fromJson(body, List.class);
        assertNotNull(list);
    }


//    @Test
//    public void supplyAnInvalidOrderTriggersValidationFailure() {
//        HttpClientResponseException thrown = assertThrows(HttpClientResponseException.class, () -> {
//            client.toBlocking().exchange(HttpRequest.GET("/users/list?order=foo"));
//        });
//
//        assertNotNull(thrown.getResponse());
//        assertEquals(HttpStatus.BAD_REQUEST, thrown.getStatus());
//    }
//
//    @Test
//    public void testFindNonExistingUserReturns404() {
//        HttpClientResponseException thrown = assertThrows(HttpClientResponseException.class, () -> {
//            client.toBlocking().exchange(HttpRequest.GET("/users/99"));
//        });
//
//        assertNotNull(thrown.getResponse());
//        assertEquals(HttpStatus.NOT_FOUND, thrown.getStatus());
//    }
//
//    @Test
//    public void testUserCrudOperations() {
//
//        List<Long> userIds = new ArrayList<>();
//
//        HttpRequest request = HttpRequest.POST("/users", new UserCreateForm("DevOps"));
//        HttpResponse response = client.toBlocking().exchange(request);
//        userIds.add(entityId(response));
//
//        assertEquals(HttpStatus.CREATED, response.getStatus());
//
//        request = HttpRequest.POST("/users", new UserCreateForm("Microservices"));
//        response = client.toBlocking().exchange(request);
//
//        assertEquals(HttpStatus.CREATED, response.getStatus());
//
//        Long id = entityId(response);
//        userIds.add(id);
//        request = HttpRequest.GET("/users/" + id);
//
//        User user = client.toBlocking().retrieve(request, User.class);
//
//        assertEquals("Microservices", user.getEmail());
//
//        request = HttpRequest.PUT("/users", new UserUpdateForm(id, "Micro-services"));
//        response = client.toBlocking().exchange(request);
//
//        assertEquals(HttpStatus.NO_CONTENT, response.getStatus());
//
//        request = HttpRequest.GET("/users/" + id);
//        user = client.toBlocking().retrieve(request, User.class);
//        assertEquals("Micro-services", user.getEmail());
//
//        request = HttpRequest.GET("/users/list");
//        List<User> users = client.toBlocking().retrieve(request, Argument.of(List.class, User.class));
//
//        assertEquals(2, users.size());
//
//        request = HttpRequest.GET("/users/list?max=1");
//        users = client.toBlocking().retrieve(request, Argument.of(List.class, User.class));
//
//        assertEquals(1, users.size());
//        assertEquals("DevOps", users.get(0).getEmail());
//
//        request = HttpRequest.GET("/users/list?max=1&order=desc&sort=name");
//        users = client.toBlocking().retrieve(request, Argument.of(List.class, User.class));
//
//        assertEquals(1, users.size());
//        assertEquals("Micro-services", users.get(0).getEmail());
//
//        request = HttpRequest.GET("/users/list?max=1&offset=10");
//        users = client.toBlocking().retrieve(request, Argument.of(List.class, User.class));
//
//        assertEquals(0, users.size());
//
//        // cleanup:
//        for (Long userId : userIds) {
//            request = HttpRequest.DELETE("/users/" + userId);
//            response = client.toBlocking().exchange(request);
//            assertEquals(HttpStatus.NO_CONTENT, response.getStatus());
//        }
//    }

    protected Long entityId(HttpResponse response) {
        String path = "/users/";
        String value = response.header(HttpHeaders.LOCATION);
        if (value == null) {
            return null;
        }
        int index = value.indexOf(path);
        if (index != -1) {
            return Long.valueOf(value.substring(index + path.length()));
        }
        return null;
    }
}
