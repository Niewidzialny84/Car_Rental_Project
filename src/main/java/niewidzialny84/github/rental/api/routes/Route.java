package niewidzialny84.github.rental.api.routes;

import com.google.gson.Gson;
import niewidzialny84.github.rental.service.CarService;
import niewidzialny84.github.rental.service.ClientService;
import niewidzialny84.github.rental.service.RentedCarService;

public abstract class Route {
    protected final CarService carService;
    protected final ClientService clientService;
    protected final RentedCarService rentedCarService;
    protected final Gson gson = new Gson();

    public Route(CarService carService, ClientService clientService, RentedCarService rentedCarService) {
        this.carService = carService;
        this.clientService = clientService;
        this.rentedCarService = rentedCarService;
    }
}
