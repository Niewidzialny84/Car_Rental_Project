package niewidzialny84.github.rental;

import niewidzialny84.github.rental.entity.Car;
import niewidzialny84.github.rental.entity.Client;
import niewidzialny84.github.rental.entity.RentedCar;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

/*
Test class used a simple testing ground and some deciding factor how to go with app
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DatabaseTest {
    private static SessionFactory factory;
    private static Car car;
    private static Client client;

    @BeforeAll
    public static void setUp() throws Exception {
        Configuration configuration = new Configuration();
        configuration.configure();

        factory = new Configuration().configure().buildSessionFactory();

        car = new Car("BWM","Model X");
        client = new Client("John","Connor");
    }

    @AfterAll
    public static void tearDown() throws Exception {
        if (factory != null) {
            factory.close();
        }
    }

    protected Session session;
    protected Transaction tx;

    @BeforeEach
    public void init() {
        session = factory.openSession();
        tx = session.beginTransaction();
    }

    @AfterEach
    public void finish() {
        tx.commit();
        session.close();
    }

    @Test
    @Order(1)
    public void testCarAdd() {
        session.save(car);
    }

    @Test
    @Order(2)
    public void testCarList() {
        Query q = session.createQuery("FROM Car WHERE brand=:brand and model=:model");
        q.setParameter("brand",car.getBrand());
        q.setParameter("model",car.getModel());
        List cars = q.getResultList();
        for(Object c : cars) {
            if (car.equals(c)) {
                q = session.createQuery("DELETE FROM Car WHERE brand=:brand and model=:model");
                q.setParameter("brand",car.getBrand());
                q.setParameter("model",car.getModel());
                q.executeUpdate();
                return;
            }
        }
        fail("Did not find car");
    }

    @Test
    @Order(3)
    public void testClientAdd() {
        session.save(client);
    }

    @Test
    @Order(4)
    public void testClientDelete() {
        Query q = session.createQuery("DELETE FROM Client WHERE firstName=:firstName and lastName=:lastName");
        q.setParameter("firstName",client.getFirstName());
        q.setParameter("lastName",client.getLastName());
        q.executeUpdate();
    }

    @Test
    @Order(5)
    public void testRentingCar() {
        session.save(client);
        session.save(car);
        RentedCar cars = new RentedCar(car,client);
        session.save(cars);

        System.out.println(cars);

        cars.setReturnDate();
        System.out.println(cars);

        session.delete(cars);
        session.delete(car);
        session.delete(client);
    }
}
