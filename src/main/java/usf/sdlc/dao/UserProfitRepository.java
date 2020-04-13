package usf.sdlc.dao;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import io.micronaut.data.repository.CrudRepository;
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


}
