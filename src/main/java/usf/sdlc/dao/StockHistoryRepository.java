package usf.sdlc.dao;

import io.micronaut.configuration.hibernate.jpa.scope.CurrentSession;
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


    @PersistenceContext
    private EntityManager entityManager;
    private final ApplicationConfiguration applicationConfiguration;

    public StockHistoryRepository(@CurrentSession EntityManager entityManager,
                                      ApplicationConfiguration applicationConfiguration) {
        this.entityManager = entityManager;
        this.applicationConfiguration = applicationConfiguration;
    }

    public List<StockHistory> customFindByLatestTime(Date date) {
        System.out.println("Timestamp: " + date);
        TypedQuery<StockHistory> query = entityManager
                .createQuery("SELECT s FROM StockHistory s WHERE s.latestTime = :date order by s.dividendYield DESC ", StockHistory.class)
                .setParameter("date", date);
        return query.getResultList();
    }


    //StockHistory save(StockHistoryForm shf);
}