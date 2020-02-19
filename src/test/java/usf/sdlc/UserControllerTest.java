package usf.sdlc;

import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import usf.sdlc.form.UserCreateForm;
import usf.sdlc.form.UserUpdateForm;
import usf.sdlc.model.User;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@MicronautTest
public class UserControllerTest {

    @Inject
    @Client("/")
    HttpClient client;

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
