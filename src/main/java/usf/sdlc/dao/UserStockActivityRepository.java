package usf.sdlc.dao;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import usf.sdlc.model.UserStockActivity;

@Repository
public interface UserStockActivityRepository extends CrudRepository<UserStockActivity, Long> {
}
