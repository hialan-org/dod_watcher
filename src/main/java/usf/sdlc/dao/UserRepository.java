package usf.sdlc.dao;

import usf.sdlc.model.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> findById(@NotNull Long id);

    User save(@NotBlank String email);

    void deleteById(@NotNull Long id);

    List<User> findAll(@NotNull SortingAndOrderArguments args);

    int update(@NotNull Long id, @NotBlank String email);
}
