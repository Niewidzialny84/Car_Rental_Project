package niewidzialny84.github.rental.api;

import com.google.gson.Gson;
import niewidzialny84.github.rental.api.model.Message;
import niewidzialny84.github.rental.entity.Car;
import niewidzialny84.github.rental.entity.Client;
import niewidzialny84.github.rental.service.CarService;
import niewidzialny84.github.rental.service.ClientService;
import niewidzialny84.github.rental.service.RentedCarService;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import static spark.Spark.*;

public class Controller {
    private final int PORT;
    private final Logger logger = LoggerFactory.getLogger(Controller.class);

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
            before("/*", (req,res)->{
                entityManager.getTransaction().begin();
            });
            path("/car", () -> {
                get("", (req,res) -> carService.getAllCars(), gson::toJson);
                get("/:id", (req,res) -> {
                    var x = carService.getCarById(Long.decode(req.params(":id")));
                    if (x != null) {
                        return x;
                    } else {
                        res.status(404);
                        return new Message("Car Not Found");
                    }
                }, gson::toJson);
                post("", (req,res) -> {
                    if(req.queryParams("brand") != null && req.queryParams("model") != null) {
                        Car car = carService.saveCar(new Car(req.queryParams("brand"),req.queryParams("model")));
                        res.status(201);
                        res.header("Location",req.url()+"/"+car.getId());
                        return car;
                    } else {
                        res.status(400);
                        return new Message("Invalid parameters");
                    }
                }, gson::toJson);
                patch("/:id", (req,res) -> {
                    Car car = carService.getCarById(Long.decode(req.params(":id")));
                    if (car == null) {
                        res.status(404);
                        return new Message("Car Not Found");
                    }

                    if (req.queryParams("brand") != null) {
                        car.setBrand(req.queryParams("brand"));
                    }

                    if (req.queryParams("model") != null) {
                        car.setModel(req.queryParams("model"));
                    }
                    carService.saveCar(car);
                    return car;
                },gson::toJson);
                delete("/:id",(req,res) -> {
                    var x = carService.getCarById(Long.decode(req.params(":id")));
                    if (x != null) {
                        carService.deleteCar(x);
                        res.status(204);
                        return "";
                    } else {
                        res.status(404);
                        return new Message("Car Not Found");
                    }
                },gson::toJson);
            });
            path("/client", ()-> {
                get("", (req,res) -> {
                    if (req.queryParams("firstName") != null && req.queryParams("lastName") != null) {
                        try {
                            return clientService.getClientByName(req.queryParams("firstName"), req.queryParams("lastName"));
                        } catch (NoResultException e) {
                            res.status(404);
                            return new Message("User Not found");
                        }
                    } else {
                       return clientService.getAllClients();
                    }
                }, gson::toJson);
                get("/:id",(req,res) -> {
                    var x = clientService.getClientById(Long.decode(req.params(":id")));
                    if (x != null) {
                        return x;
                    } else {
                        res.status(404);
                        return new Message("Client Not Found");
                    }
                }, gson::toJson);
                post("", (req,res) -> {
                    if(req.queryParams("firstName") != null && req.queryParams("lastName") != null) {
                        var client = clientService.saveClient(new Client(req.queryParams("firstName"),req.queryParams("lastName")));
                        res.status(201);
                        res.header("Location",req.url()+"/"+client.getId());
                        return client;
                    } else {
                        res.status(400);
                        return new Message("Invalid parameters");
                    }
                }, gson::toJson);
                delete("/:id", (req,res) -> {
                    var x = clientService.getClientById(Long.decode(req.params(":id")));
                    if (x != null) {
                        clientService.deleteClient(x);
                        res.status(204);
                        return "";
                    } else {
                        res.status(404);
                        return new Message("Car Not Found");
                    }
                },gson::toJson);
            });
            path("/rental", () -> {
                get("", (req,res) -> rentedCarService.getAllRentedCars(),gson::toJson);
                get("/:id", (req,res) -> {
                    var x = rentedCarService.getRentedCarById(Long.decode(req.params(":id")));
                    if (x != null) {
                        return x;
                    } else {
                        res.status(404);
                        return new Message("Rental Record Not Found");
                    }
                }, gson::toJson);
                post("", (req,res)->null,gson::toJson);
                post("/:id", (req,res)->null,gson::toJson);
            });
            after("/*",(req,res)-> {
                res.type("application/json");
                entityManager.getTransaction().commit();
                logger.info(req.ip()+"["+req.requestMethod()+"] ["+res.status()+"] "+req.url());
            });
        });
    }
}
