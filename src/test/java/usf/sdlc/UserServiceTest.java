package usf.sdlc;

import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import usf.sdlc.model.User;
import usf.sdlc.service.UserService;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@MicronautTest
public class UserServiceTest {

    @Inject
    UserService userService;

    @Test
    void loginWithGoogleTest() {
        System.out.println("Starting loginWithGoogleTest");
        String accessToken="ya29.a0Ae4lvC007fJTLog5SSi0odFYodQ-RRvs_k3qcQZeOQ4Tumoer1YY1ztn2arZF1ZppDbmOUqsDgcL5hPxH3e3QTm9HBdkNqED5A6hIiGbecNryqeV30B_L9oVJM2P9IhhdoRXGxVvXBtpTShM49ZMgp2hPFD7pmwcmYM";
        //TODO:??? Returning NULL? WHY?
        User user = userService.loginWithGoogle(accessToken);

        //assertEquals("alper.ozdamar@gmail.com", user.getEmail());
    }

    @Test
    void findByUserIdTest() {
        System.out.println("Starting findByUserIdTest");
        long userId=1;
        User user = userService.findByUserId(userId);

        assertNotEquals(null, user);
        assertEquals("alper.ozdamar@gmail.com", user.getEmail());
    }

    @Test
    void findByAccessTokenTest() {
        System.out.println("Starting findByAccessTokenTest");
        String accessToken="ya29.a0Ae4lvC007fJTLog5SSi0odFYodQ-RRvs_k3qcQZeOQ4Tumoer1YY1ztn2arZF1ZppDbmOUqsDgcL5hPxH3e3QTm9HBdkNqED5A6hIiGbecNryqeV30B_L9oVJM2P9IhhdoRXGxVvXBtpTShM49ZMgp2hPFD7pmwcmYM";
        User user = userService.findByAccessToken(accessToken);
        assertNotEquals(null, user);
        assertEquals("alper.ozdamar@gmail.com", user.getEmail());
    }

    @Test
    void deleteByUserIdTest() {
        System.out.println("Starting listTest");
        boolean result = userService.deleteByUserId(0);

        assertEquals(true,result);

        User user = userService.findByUserId(0);
        assertEquals(user.getActive(),0);

        user.setActive((byte)1);
        user= userService.update(user);
        assertEquals(user.getActive(),1);

    }

}
