package niewidzialny84.github.rental.api.routes;

import niewidzialny84.github.rental.api.model.Message;
import niewidzialny84.github.rental.api.routes.Route;
import niewidzialny84.github.rental.entity.Client;
import niewidzialny84.github.rental.service.CarService;
import niewidzialny84.github.rental.service.ClientService;
import niewidzialny84.github.rental.service.RentedCarService;
import spark.RouteGroup;

import javax.persistence.NoResultException;

import static spark.Spark.*;
import static spark.Spark.delete;

public class ClientRoute extends Route implements RouteGroup {
    public ClientRoute(CarService carService, ClientService clientService, RentedCarService rentedCarService) {
        super(carService, clientService, rentedCarService);
    }

    @Override
    public void addRoutes() {
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
        patch("/:id",(req,res)-> {
            var client = clientService.getClientById(Long.decode(req.params(":id")));
            if (client == null) {
                res.status(404);
                return new Message("Client Not Found");
            }

            if (req.queryParams("firstName") != null) {
                client.setFirstName(req.queryParams("firstName"));
            }

            if (req.queryParams("lastName") != null) {
                client.setLastName(req.queryParams("lastName"));
            }
            clientService.saveClient(client);
            return client;
        },gson::toJson);
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
    }
}
