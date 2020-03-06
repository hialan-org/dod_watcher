package usf.sdlc.dao;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import usf.sdlc.model.UserProfit;

@Repository
public interface UserProfitRepository extends CrudRepository<UserProfit, Long> {
}
