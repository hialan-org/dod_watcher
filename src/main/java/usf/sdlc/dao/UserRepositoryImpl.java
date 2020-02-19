//package usf.sdlc.dao;
//
//import io.micronaut.configuration.hibernate.jpa.scope.CurrentSession;
//import io.micronaut.spring.tx.annotation.Transactional;
//import usf.sdlc.config.ApplicationConfiguration;
//import usf.sdlc.model.User;
//
//import javax.inject.Singleton;
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.persistence.TypedQuery;
//import javax.validation.constraints.NotBlank;
//import javax.validation.constraints.NotNull;
//import java.sql.Timestamp;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//@Singleton
//public class UserRepositoryImpl implements UserRepository {
//
//    @PersistenceContext
//    private EntityManager entityManager;
//    private final ApplicationConfiguration applicationConfiguration;
//
//    public UserRepositoryImpl(@CurrentSession EntityManager entityManager,
//                              ApplicationConfiguration applicationConfiguration) {
//        this.entityManager = entityManager;
//        this.applicationConfiguration = applicationConfiguration;
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public Optional<User> findById(@NotNull Long userId) {
//        return Optional.ofNullable(entityManager.find(User.class, userId));
//    }
//
//    @Override
//    @Transactional
//    public User create(@NotBlank String email) {
//        Timestamp createdDate = new Timestamp(System.currentTimeMillis());
//        User user = new User(email, createdDate);
//        entityManager.persist(user);
//        return user;
//    }
//
//    @Override
//    @Transactional
//    public void deleteById(@NotNull Long userId) {
//        findById(userId).ifPresent(user -> entityManager.remove(user));
//    }
//
//    private final static List<String> VALID_PROPERTY_NAMES = Arrays.asList("id", "email");
//
//    @Transactional(readOnly = true)
//    public List<User> findAll(@NotNull SortingAndOrderArguments args) {
//        String qlString = "SELECT u FROM User as u";
//        if (args.getOrder().isPresent() && args.getSort().isPresent() && VALID_PROPERTY_NAMES.contains(args.getSort().get())) {
//            qlString += " ORDER BY u." + args.getSort().get() + " " + args.getOrder().get().toLowerCase();
//        }
//        TypedQuery<User> query = entityManager.createQuery(qlString, User.class);
//        query.setMaxResults(args.getMax().orElseGet(applicationConfiguration::getMax));
//        args.getOffset().ifPresent(query::setFirstResult);
//
//        return query.getResultList();
//    }
//
//    @Override
//    @Transactional
//    public int update(@NotNull Long userId, @NotBlank String email) {
//        return entityManager.createQuery("UPDATE User u SET email = :email where userId = :userId")
//                .setParameter("email", email)
//                .setParameter("userId", userId)
//                .executeUpdate();
//    }
//}
