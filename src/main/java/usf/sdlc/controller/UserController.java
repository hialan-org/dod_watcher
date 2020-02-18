package usf.sdlc.controller;

import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import usf.sdlc.dao.SortingAndOrderArguments;
import usf.sdlc.dao.UserRepository;
import usf.sdlc.dao.UserSaveCommand;
import usf.sdlc.dao.UserUpdateCommand;
import usf.sdlc.model.User;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Controller("/users")
public class UserController {

    protected final UserRepository userRepository;

    public UserController(UserRepository genreRepository) {
        this.userRepository = genreRepository;
    }

    @Get("/{id}")
    public User show(Long userId) {
        return userRepository
                .findById(userId)
                .orElse(null);
    }

    @Put("/")
    public HttpResponse update(@Body @Valid UserUpdateCommand command) {
        int numberOfEntitiesUpdated = userRepository.update(command.getUserId(), command.getEmail());

        return HttpResponse
                .noContent()
                .header(HttpHeaders.LOCATION, location(command.getUserId()).getPath());
    }

    @Get(value = "/list{?args*}")
    public List<User> list(@Valid SortingAndOrderArguments args) {
        return userRepository.findAll(args);
    }

    @Post("/")
    public HttpResponse<User> save(@Body @Valid UserSaveCommand cmd) {
        User user = userRepository.save(cmd.getEmail());

        return HttpResponse
                .created(user)
                .headers(headers -> headers.location(location(user.getUserId())));
    }

    @Delete("/{id}")
    public HttpResponse delete(Long userId) {
        userRepository.deleteById(userId);
        return HttpResponse.noContent();
    }

    protected URI location(Long userId) {
        return URI.create("/users/" + userId);
    }

    protected URI location(User user) {
        return location(user.getUserId());
    }
}
