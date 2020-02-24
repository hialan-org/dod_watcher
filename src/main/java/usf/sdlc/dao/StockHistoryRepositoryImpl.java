package usf.sdlc.dao;

import io.micronaut.configuration.hibernate.jpa.scope.CurrentSession;
import io.micronaut.data.annotation.Repository;
import usf.sdlc.config.ApplicationConfiguration;
import usf.sdlc.model.StockHistory;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.sql.Timestamp;
import java.util.List;

public class StockHistoryRepositoryImpl {
}
