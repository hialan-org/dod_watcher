package usf.sdlc.dao;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import usf.sdlc.model.UserProfit;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public abstract class UserProfitRepository implements JpaRepository<UserProfit, Long> {

    private final EntityManager entityManager;

    public UserProfitRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public int customDeleteUserProfitOnDate(Long uid, Date d) {
        Query query = entityManager
                .createQuery("DELETE FROM UserProfit WHERE date= :d AND userId=:uid")
                .setParameter("d", d).setParameter("uid", uid);

        int deletedCount = query.executeUpdate();
        return deletedCount;
    }

    @Transactional
    public List<UserProfit> customGetUserProfitHistory(long userId, long stockId, Date startDate, Date endDate) {
//                               SELECT * FROM user_profit up WHERE up.user_id=1 and up.stock_id=0 and up.date between '2020-03-06' and '2020-03-18';
        TypedQuery<UserProfit> query = entityManager
                .createQuery("SELECT up FROM UserProfit up WHERE up.userId=:userId and up.stockId=:stockId and up.date between :startDate and :endDate" , UserProfit.class)
                .setParameter("userId", userId)
                .setParameter("stockId", stockId)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate);

        return query.getResultList();
    }

    @Transactional
    public List<Double> countTotalAmountOfUserMoney() {
        TypedQuery<Double> query = entityManager
                .createQuery("SELECT SUM(up.totalAmount) FROM UserProfit up group by up.date order by up.date desc" , Double.class);

        return query.getResultList();
    }
}
