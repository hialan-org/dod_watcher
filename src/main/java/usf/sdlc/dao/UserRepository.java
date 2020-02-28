package usf.sdlc.dao;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import io.micronaut.data.model.Page;
import io.micronaut.data.model.Pageable;
import io.micronaut.data.model.Slice;
import io.micronaut.data.repository.CrudRepository;
import usf.sdlc.model.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Repository
public abstract class UserRepository implements JpaRepository<User, Long> {

    public abstract Optional<User> findById(@NotNull Long userId);

    public abstract void deleteById(@NotNull Long userId);

    public abstract Optional<User> findByEmail(@NotNull String email);

    public abstract Page<User> findByEmailLike(String email, Pageable pageable);

    public abstract List<User> findByEmailLike(String email);
}
