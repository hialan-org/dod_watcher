package usf.sdlc.dao;

import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import io.micronaut.data.repository.CrudRepository;
import usf.sdlc.form.StockHistoryForm;
import usf.sdlc.model.StockHistory;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Repository
public interface StockHistoryRepository extends JpaRepository<StockHistory, Long> {

    StockHistory findById(String id);

    @Query("SELECT s FROM StockHistory s where s.latestTime = :timestamp ORDER BY s.dividendYield DESC")
//    @Query("SELECT s FROM StockHistory s")
    List<StockHistory> findByLatestTime(Timestamp timestamp);


    //StockHistory save(StockHistoryForm shf);
}