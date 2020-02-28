package usf.sdlc.dao;

import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import io.micronaut.data.repository.CrudRepository;
import usf.sdlc.config.ApplicationConfiguration;
import usf.sdlc.form.StockHistoryForm;
import usf.sdlc.model.StockHistory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Repository
public abstract class StockHistoryRepository implements JpaRepository<StockHistory, Long> {

//    abstract StockHistory findById(String id);
//
//    @Query("SELECT s FROM StockHistory s where s.latestTime = :timestamp ORDER BY s.dividendYield DESC")
////    @Query("SELECT s FROM StockHistory s")
//    public abstract List<StockHistory> findByLatestTime(Timestamp timestamp);

    private final EntityManager entityManager;

    public StockHistoryRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public List<StockHistory> customFindByLatestTime(Date date, int numOfResult) {
        System.out.println("Timestamp: " + date);
        TypedQuery<StockHistory> query = entityManager
                .createQuery("SELECT sh FROM StockHistory sh " +
                        "WHERE sh.latestTime = :date AND sh.stockId<>(SELECT s.stockId FROM Stock s WHERE s.symbol='SPY')" +
                        "order by sh.dividendYield DESC", StockHistory.class)
                .setParameter("date", date)
                .setMaxResults(numOfResult);
        return query.getResultList();
    }


    //StockHistory save(StockHistoryForm shf);
}