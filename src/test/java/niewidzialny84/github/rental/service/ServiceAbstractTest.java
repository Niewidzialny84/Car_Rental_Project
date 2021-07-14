package niewidzialny84.github.rental.service;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import javax.persistence.EntityManager;

public abstract class ServiceAbstractTest {
    protected final SessionFactory factory = new Configuration().configure().buildSessionFactory();
    protected final EntityManager entityManager = factory.createEntityManager();

    @BeforeEach
    public void beforeEach() {
        entityManager.getTransaction().begin();
    }

    @AfterEach
    public void afterEach() {
        entityManager.getTransaction().commit();
    }
}
