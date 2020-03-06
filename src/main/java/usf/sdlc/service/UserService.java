package usf.sdlc.service;

import io.micronaut.data.model.Pageable;
import usf.sdlc.model.User;

import java.util.List;

public interface UserService {
    User loginWithGoogle(String accessToken);
    User findByUserId(long userId);
    List<User> list(String email, int page, int size);
    void deleteByUserId(long userId);
    User update(User user);
    User findByAccessToken(String accessToken);
    boolean authorizeUser(String authHeader, String[] roles);
}
