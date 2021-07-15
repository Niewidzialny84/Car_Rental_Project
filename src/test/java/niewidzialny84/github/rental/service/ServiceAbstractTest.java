package niewidzialny84.github.rental.service;

import niewidzialny84.github.rental.entity.Car;
import niewidzialny84.github.rental.entity.Client;
import niewidzialny84.github.rental.entity.RentedCar;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import javax.persistence.EntityManager;

public abstract class ServiceAbstractTest {
    protected static final SessionFactory factory = new Configuration().configure().buildSessionFactory();
    protected final EntityManager entityManager = factory.createEntityManager();

    @BeforeAll
    public static void beforeAll() {
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();
        var c = new Car("Tesla","Model Y");
        var cl = new Client("Joe","Moe");
        var car = new RentedCar(c,cl);
        entityManager.persist(c);
        entityManager.persist(cl);
        entityManager.persist(car);

        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @AfterAll
    public static void afterAll() {
        EntityManager entityManager = factory.createEntityManager();
        if(!entityManager.getTransaction().isActive())
            entityManager.getTransaction().begin();

        entityManager.createQuery("DELETE FROM Car").executeUpdate();
        entityManager.createQuery("DELETE FROM Client").executeUpdate();
        entityManager.createQuery("DELETE FROM RentedCar").executeUpdate();

        if(entityManager.getTransaction().isActive())
            entityManager.getTransaction().commit();
        entityManager.close();
    }

    @BeforeEach
    public void beforeEach() {
        entityManager.getTransaction().begin();
    }

    @AfterEach
    public void afterEach() {
        entityManager.getTransaction().commit();
    }
}
