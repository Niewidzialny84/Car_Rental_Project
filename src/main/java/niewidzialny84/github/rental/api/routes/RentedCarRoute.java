package niewidzialny84.github.rental.api.routes;

import niewidzialny84.github.rental.api.model.Message;
import niewidzialny84.github.rental.api.routes.Route;
import niewidzialny84.github.rental.service.CarService;
import niewidzialny84.github.rental.service.ClientService;
import niewidzialny84.github.rental.service.RentedCarService;
import spark.RouteGroup;

import javax.persistence.NoResultException;

import static spark.Spark.get;
import static spark.Spark.post;

public class RentedCarRoute extends Route implements RouteGroup {
    public RentedCarRoute(CarService carService, ClientService clientService, RentedCarService rentedCarService) {
        super(carService, clientService, rentedCarService);
    }

    @Override
    public void addRoutes() {
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
        post("", (req,res)-> {
            if (req.queryParams("carId") == null || req.queryParams("clientId") == null) {
                res.status(400);
                return new Message("Invalid parameters");
            }

            var car = carService.getCarById(Long.decode(req.queryParams("carId")));
            var client = clientService.getClientById(Long.decode(req.queryParams("clientId")));

            if(car == null || client == null) {
                res.status(404);
                return new Message("Not found Identifiers");
            }

            try {
                return rentedCarService.rentCar(car, client);
            } catch (NoResultException e) {
                res.status(400);
                return new Message(e.getMessage());
            }
        },gson::toJson);
        post("/:id", (req,res)-> {
            try {
                return rentedCarService.returnCar(Long.decode(req.params(":id")));
            } catch (NoResultException e) {
                res.status(404);
                return new Message(e.getMessage());
            }
        },gson::toJson);
    }
}
