package usf.sdlc.dao;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import usf.sdlc.model.StockHistory;

@Repository
public interface StockHistoryRepository extends CrudRepository<StockHistory, Long> {

    StockHistory findById(String id);

    //StockHistory save(StockHistoryForm shf);
}