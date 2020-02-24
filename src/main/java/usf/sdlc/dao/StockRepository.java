package usf.sdlc.dao;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import usf.sdlc.model.Stock;

@Repository
public interface StockRepository extends CrudRepository<Stock, Long> {

    Stock findBySymbol(String symbol);


}
