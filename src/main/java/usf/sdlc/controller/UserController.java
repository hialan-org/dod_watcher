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
import usf.sdlc.form.AddStocksForm;
import usf.sdlc.form.UserCreateForm;
import usf.sdlc.form.UserUpdateForm;
import usf.sdlc.model.User;
import usf.sdlc.service.UserStockActivityService;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Controller("/users")
public class UserController {

    @Inject
    UserRepository userRepository;

    UserStockActivityService userStockActivityService;

    public UserController(UserStockActivityService userStockActivityService){
        this.userStockActivityService = userStockActivityService;
    }

    @Get("/{userId}")
    public User show(Long userId) {
        return userRepository
                .findById(userId)
                .orElse(null);
    }

    @Put("/")
    public HttpResponse update(@Body @Valid UserUpdateForm command) {
        Optional<User> user = userRepository.findById(command.getUserId());
        if(user!=null){
            user.get().setEmail(command.getEmail());
        }
        userRepository.update(user.get());

        return HttpResponse
                .noContent()
                .header(HttpHeaders.LOCATION, location(command.getUserId()).getPath());
    }

    @Get(value = "/list{?args*}")
    public List<User> list(@Valid SortingAndOrderArguments args) {
        return (List<User>) userRepository.findAll();
    }

    @Post("/")
    public HttpResponse<User> save(@Body @Valid UserCreateForm cmd) {
        User temp = new User();
        temp.setEmail(cmd.getEmail());
        temp.setCreatedDate(new Timestamp(System.currentTimeMillis()));
        User user = userRepository.save(temp);

        return HttpResponse
                .created(user)
                .headers(headers -> headers.location(location( user.getUserId())));
    }

    @Delete("/{userId}")
    public HttpResponse delete(Long userId) {

        userRepository.deleteById(userId);
        return HttpResponse.noContent();
    }

    @Post("/{userId}/addStock")
    public HttpResponse addStock(Long userId, @Body @Valid AddStocksForm stocks) {
        System.out.println(stocks);
        userStockActivityService.saveAll(userId, stocks);
        return HttpResponse.ok();
    }

    protected URI location(Long userId) {
        return URI.create("/users/" + userId);
    }

    protected URI location(User user) {
        return location(user.getUserId());
    }
}
