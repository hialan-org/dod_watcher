package usf.sdlc.dao;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import usf.sdlc.model.UserStock;
import usf.sdlc.model.UserStockId;

@Repository
public interface UserStockRepository extends CrudRepository<UserStock, UserStockId> {
}
