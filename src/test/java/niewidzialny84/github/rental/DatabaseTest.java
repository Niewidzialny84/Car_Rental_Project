package niewidzialny84.github.rental;

import junit.framework.TestCase;
import niewidzialny84.github.rental.entity.Car;
import niewidzialny84.github.rental.entity.Client;
import niewidzialny84.github.rental.entity.RentedCars;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.hibernate.cfg.Configuration;

public class DatabaseTest extends TestCase {
    private SessionFactory factory;

    @Override
    protected void setUp() throws Exception {
        Configuration configuration = new Configuration()
                .addAnnotatedClass(Car.class)
                .addAnnotatedClass(Client.class)
                .addAnnotatedClass(RentedCars.class)
                .setProperty("hibernate.connection.driver_class", "org.sqlite.JDBC")
                .setProperty("hibernate.connection.url", "jdbc:sqlite:sqlite.db")
                .setProperty("hibernate.dialect", "org.hibernate.dialect.SQLiteDialect")
                .setProperty("hibernate.show_sql", "true")
                .setProperty("hibernate.hdm2ddl.auto", "create-drop");
        configuration.configure();

        factory = new Configuration().configure().buildSessionFactory();
    }

    @Override
    protected void tearDown() throws Exception {
        if (factory != null) {
            factory.close();
        }
    }

    public void testCarAdd() {
        Session session = factory.openSession();
        session.getTransaction();
        session.persist(new Car("BWM","Model W"));
        session.getTransaction().commit();
        session.close();
    }


}
