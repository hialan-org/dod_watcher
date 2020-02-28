package usf.sdlc.service;

import usf.sdlc.Constant;
import usf.sdlc.Utils;
import usf.sdlc.dao.UserRepository;
import usf.sdlc.form.GoogleResponse;
import usf.sdlc.model.User;

import javax.inject.Inject;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    @Inject
    UserRepository userRepository;

    @Override
    public User loginWithGoogle(String accessToken) {
        java.util.Date utilDate = new java.util.Date();
        GoogleResponse googleResponse = Utils.validateAccessToken(accessToken);
        User user = null;
        if(googleResponse!=null){
            if(googleResponse.getAud().equals(Constant.GOOGLE_CLIENT_ID)){
                Optional<User> queryResult = userRepository.findByEmail(googleResponse.getEmail());
                if(queryResult.isPresent()){
                    System.out.println("Is present");
                    user = queryResult.get();
                    user.setAccessToken(accessToken);
                    userRepository.update(user);
                } else {
                    user = new User(googleResponse.getEmail(), new java.sql.Date(utilDate.getTime()),
                            accessToken, "");
                    userRepository.save(user);
                }
                return user;
            }
        }

        return user;
    }
}
