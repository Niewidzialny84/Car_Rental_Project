package niewidzialny84.github.rental.api;

import com.google.gson.Gson;
import niewidzialny84.github.rental.service.CarService;
import niewidzialny84.github.rental.service.ClientService;
import niewidzialny84.github.rental.service.RentedCarService;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import static spark.Spark.*;

public class Controller {
    private final int PORT;

    private final SessionFactory factory;
    private final EntityManager entityManager;

    private final CarService carService;
    private final ClientService clientService;
    private final RentedCarService rentedCarService;

    private Gson gson = new Gson();

    public Controller(int port) {
        this.PORT = port;

        Configuration configuration = new Configuration();
        configuration.configure();

        factory = new Configuration().configure().buildSessionFactory();
        entityManager = factory.createEntityManager();

        this.carService = new CarService(this.entityManager);
        this.clientService = new ClientService(this.entityManager);
        this.rentedCarService = new RentedCarService(this.entityManager);

        init();
    }

    public void init() {
        port(PORT);

        path("/api", () -> {
            get("", (req,res) -> "Homepage");
            path("/car", () -> {
                get("", (req,res) -> carService.getAllCars(), gson::toJson);
                get("/:id", (req,res) -> carService.getCarById(Long.decode(req.params(":id"))), gson::toJson);
                post("", (req,res) -> null
                );
                patch("", (req,res) -> null
                );
                delete("/:id",(req,res) -> {
                    carService.deleteCar(carService.getCarById(Long.decode(req.params(":id"))));
                    res.status(204);
                    return true;
                });
            });
            path("/client", ()-> {
                get("", (req,res) -> {
                    if (req.queryParams("firstName") != null && req.queryParams("lastName") != null) {
                        try {
                            return clientService.getClientByName(req.queryParams("firstName"), req.queryParams("lastName"));
                        } catch (NoResultException e) {
                            res.status(404);
                            return "User Not found";
                        }
                    } else {
                       return clientService.getAllClients();
                    }
                }, gson::toJson);
                get("/:id",(req,res) -> clientService.getClientById(Long.decode(req.params(":id"))), gson::toJson);
            });
            path("/remtal", () -> {
                //...
            });
        });
    }
}
