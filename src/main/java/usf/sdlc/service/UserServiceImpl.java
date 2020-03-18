package usf.sdlc.service;

import io.micronaut.data.model.Pageable;
import io.micronaut.http.HttpResponse;
import usf.sdlc.config.Constant;
import usf.sdlc.utils.Utils;
import usf.sdlc.dao.UserRepository;
import usf.sdlc.form.GoogleResponse;
import usf.sdlc.model.User;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    @Inject
    UserRepository userRepository;
    @Inject
    Utils utils;

    @Override
    public User loginWithGoogle(String accessToken) {
        java.util.Date utilDate = new java.util.Date();
        GoogleResponse googleResponse = utils.validateAccessToken(accessToken);
        User user = null;
        if(googleResponse!=null){
            if(googleResponse.getAud().equals(Constant.GOOGLE_CLIENT_ID) || googleResponse.getAud().equals(Constant.EXPO_CLIENT_ID)){
                Optional<User> queryResult = userRepository.findByEmail(googleResponse.getEmail());
                if(queryResult.isPresent()){ //Update user access token
                    user = queryResult.get();
                    user.setAccessToken(accessToken);
                    userRepository.update(user);
                } else { //Create new user in db if user not exist
                    user = new User(googleResponse.getEmail(), new java.sql.Date(utilDate.getTime()),
                            accessToken, "", Constant.ROLE_USER);
                    userRepository.save(user);
                }
                return user;
            }
        }
        return user;
    }

    @Override
    public User findByUserId(long userId) {
        User user = userRepository.findById(userId).orElse(null);
        return user;
    }

    @Override
    public List<User> list(String email, int page, int size) {
        if(page<0){
            return null;
        }
        if(size < 0){ //Check invalid value
            return null;
        }
        if(email==null){
            email = "";
        }
        if(size == 0){
            return userRepository.findByEmailContains(email);
        }
        return userRepository.findByEmailContains(email, Pageable.from(page, size)).getContent();
    }

    @Override
    public boolean deleteByUserId(long userId) {
        User user = findByUserId(userId);
        if(user!=null){
            user.setActive(Constant.PASSIVE);
        } else {
          return false;
        }
        update(user);
     return true;
    }

    public User update(User user){
        return userRepository.update(user);
    }

    @Override
    public User findByAccessToken(String accessToken) {
        User user = userRepository.findByAccessToken(accessToken).orElse(null);
        return user;
    }

    @Override
    public boolean authorizeUser(String authHeader, String[] roles) {
        System.out.println("Checking the authorization....");
        String accessToken = authHeader.split(" ")[1];
        System.out.println(accessToken);
        User user = this.findByAccessToken(accessToken);
        if(user == null){
            return false;
        }
        if(roles.length>0){ //Check if user's role is in the roles list
            for (String role : roles) {
                if(role.equals(user.getRole())){
                    return true;
                }
            }
            return false; //If not return false
        }
        return true;
    }
}
