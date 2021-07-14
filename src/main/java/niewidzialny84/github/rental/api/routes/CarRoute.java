package niewidzialny84.github.rental.api.routes;

import niewidzialny84.github.rental.api.model.Message;
import niewidzialny84.github.rental.api.routes.Route;
import niewidzialny84.github.rental.entity.Car;
import niewidzialny84.github.rental.service.CarService;
import niewidzialny84.github.rental.service.ClientService;
import niewidzialny84.github.rental.service.RentedCarService;
import spark.RouteGroup;

import static spark.Spark.*;
import static spark.Spark.delete;

public class CarRoute extends Route implements RouteGroup {
    public CarRoute(CarService carService, ClientService clientService, RentedCarService rentedCarService) {
        super(carService, clientService, rentedCarService);
    }

    @Override
    public void addRoutes() {
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
                var car = carService.saveCar(new Car(req.queryParams("brand"),req.queryParams("model")));
                res.status(201);
                res.header("Location",req.url()+"/"+car.getId());
                return car;
            } else {
                res.status(400);
                return new Message("Invalid parameters");
            }
        }, gson::toJson);
        patch("/:id", (req,res) -> {
            var car = carService.getCarById(Long.decode(req.params(":id")));
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
    }
}
