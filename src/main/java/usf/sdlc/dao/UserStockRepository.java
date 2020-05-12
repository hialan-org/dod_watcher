package usf.sdlc.dao;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import usf.sdlc.model.UserStock;
import usf.sdlc.model.UserStockId;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public abstract class UserStockRepository implements JpaRepository<UserStock, UserStockId> {
    private final EntityManager entityManager;

    public UserStockRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public List<UserStock> findByUserIdAndIsOwned(long userId, int isOwned){
        TypedQuery<UserStock> query = entityManager
                .createQuery("SELECT us FROM UserStock us " +
                        "WHERE us.userStockId.userId= :userId AND us.isOwned= :isOwned" , UserStock.class)
                .setParameter("userId", userId)
                .setParameter("isOwned", isOwned);
        return query.getResultList();
    }

    @Transactional
    public Long getTotalNumberOfUserStocks(){
        CriteriaBuilder qb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = qb.createQuery(Long.class);
        cq.select(qb.count(cq.from(UserStock.class)));

        return entityManager.createQuery(cq).getSingleResult();
    }


    public abstract List<UserStock> findByUserStockIdAndIsOwned(UserStockId userStockId, int isOwned);
}
