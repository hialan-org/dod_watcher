package usf.sdlc.utils;

import com.google.gson.Gson;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import usf.sdlc.dao.UserRepository;
import usf.sdlc.form.GoogleResponse;
import usf.sdlc.model.User;
import usf.sdlc.service.UserService;

import javax.inject.Inject;
import java.io.IOException;

public class Utils {
    @Inject
    private UserRepository userService;

    public Utils() {
    }

    public GoogleResponse validateAccessToken(String accessToken) {
        String uri = "https://www.googleapis.com/oauth2/v3/tokeninfo?access_token=" + accessToken;
        HttpGet request = new HttpGet(uri);
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
            try (CloseableHttpResponse response = client.execute(request)) {

                // Get HttpResponse Status
                int statusCode = response.getStatusLine().getStatusCode();
                System.out.println(statusCode);
                if(statusCode!=200) { //TODO: change to constant value
                    return null;
                }
                HttpEntity entity = response.getEntity();
                Header headers = entity.getContentType();
                System.out.println(headers);

                if (entity != null) {
                    // return it as a String
                    String result = EntityUtils.toString(entity);
                    System.out.println(result);
                    Gson gson = new Gson();
                    GoogleResponse googleResponse = gson.fromJson(result, GoogleResponse.class);
                    return googleResponse;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean authorization(String authHeader, String[] roles) {
        String accessToken = authHeader.split(" ")[1];
        System.out.println(accessToken);
        User user = userService.findByAccessToken(accessToken).orElse(null);
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
