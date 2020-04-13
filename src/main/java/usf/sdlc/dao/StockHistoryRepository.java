package usf.sdlc.dao;

import io.micronaut.data.annotation.Join;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import io.micronaut.data.repository.CrudRepository;
import usf.sdlc.config.ApplicationConfiguration;
import usf.sdlc.form.StockHistoryForm;
import usf.sdlc.model.StockHistory;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Repository
public abstract class StockHistoryRepository implements JpaRepository<StockHistory, Long> {

    public abstract StockHistory findById(String id);
    private final EntityManager entityManager;

    public StockHistoryRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public List<StockHistory> customFindTopYieldByLatestTime(Date date, int numOfResult) {
        System.out.println("Timestamp: " + date);
        TypedQuery<StockHistory> query = entityManager
                .createQuery("SELECT sh FROM StockHistory sh " +
                        "WHERE sh.latestTime = :date AND sh.stock.stockId<>(SELECT s.stockId FROM Stock s WHERE s.symbol='SPY')" +
                        "order by sh.dividendYield DESC", StockHistory.class)
                .setParameter("date", date)
                .setMaxResults(numOfResult);
        return query.getResultList();
    }

    @Transactional
    public Date customFindLatestDate() {
        TypedQuery<Date> query = entityManager
                .createQuery("SELECT sh.latestTime FROM StockHistory sh order by sh.latestTime Desc" , Date.class)
                .setMaxResults(1);
        List<Date> s = query.getResultList();
        return query.getResultList().get(0);
    }

    @Transactional
    public List<StockHistory> customFindStocksHistoryOnDate(Date d) {
        TypedQuery<StockHistory> query = entityManager
                .createQuery("SELECT sh FROM StockHistory sh WHERE sh.latestTime= :d" , StockHistory.class)
                .setParameter("d", d);
//                .createQuery("SELECT sh FROM StockHistory sh WHERE sh.stockId= :stockId order by sh.latestTime Desc" , StockHistory.class)
//                .setParameter("stockId", stockId)
        return query.getResultList();
    }

    @Transactional
    public StockHistory findLatestByStockId(long stockId) {
        TypedQuery<StockHistory> query = entityManager
                .createQuery("SELECT sh FROM StockHistory  sh WHERE sh.stockId=:stockId ORDER BY sh.id DESC", StockHistory.class)
                .setParameter("stockId", stockId).setMaxResults(1);
        return query.getSingleResult();
    }

    @Transactional
    public int customDeleteStocksHistoryOnDate(Date d) {
        Query query = entityManager
                .createQuery("DELETE FROM StockHistory WHERE latestTime=:d")
                .setParameter("d",d);

        int deletedCount = query.executeUpdate();
        return deletedCount;
    }

    //StockHistory save(StockHistoryForm shf);
}