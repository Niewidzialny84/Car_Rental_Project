package niewidzialny84.github.rental.api;

import com.google.gson.Gson;
import niewidzialny84.github.rental.api.routes.CarRoute;
import niewidzialny84.github.rental.api.routes.ClientRoute;
import niewidzialny84.github.rental.api.routes.RentedCarRoute;
import niewidzialny84.github.rental.service.CarService;
import niewidzialny84.github.rental.service.ClientService;
import niewidzialny84.github.rental.service.RentedCarService;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;

import static spark.Spark.*;

public class Controller {
    private final int PORT;
    private static final Logger logger = LoggerFactory.getLogger(Controller.class);

    private final EntityManager entityManager;

    private final CarRoute carRoute;
    private final ClientRoute clientRoute;
    private final RentedCarRoute rentedCarRoute;

    private final Gson gson = new Gson();

    public Controller(int port) {
        this.PORT = port;

        Configuration configuration = new Configuration();
        configuration.configure();

        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        entityManager = factory.createEntityManager();

        CarService carService = new CarService(this.entityManager);
        ClientService clientService = new ClientService(this.entityManager);
        RentedCarService rentedCarService = new RentedCarService(this.entityManager);

        this.carRoute = new CarRoute(carService, clientService, rentedCarService);
        this.clientRoute = new ClientRoute(carService, clientService, rentedCarService);
        this.rentedCarRoute = new RentedCarRoute(carService, clientService, rentedCarService);

        init();
    }

    public void init() {
        port(PORT);

        path("/api", () -> {
            before("/*", (req,res)->{
                entityManager.getTransaction().begin();
            });
            path("/car", carRoute);
            path("/client", clientRoute);
            path("/rental", rentedCarRoute);
            after("/*",(req,res)-> {
                res.type("application/json");
                entityManager.getTransaction().commit();
                logger.info(req.ip()+"["+req.requestMethod()+"] ["+res.status()+"] "+req.url());
            });
        });
    }
}
