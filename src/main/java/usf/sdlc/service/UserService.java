package usf.sdlc.service;

import usf.sdlc.model.User;

public interface UserService {
    User loginWithGoogle(String accessToken);
}
