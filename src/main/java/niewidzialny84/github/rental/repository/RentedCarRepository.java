package niewidzialny84.github.rental.repository;

import niewidzialny84.github.rental.entity.Car;
import niewidzialny84.github.rental.entity.Client;
import niewidzialny84.github.rental.entity.RentedCar;

import java.util.List;

public interface RentedCarRepository {
    List<RentedCar> getAllRentedCars();

    RentedCar getRentedCarById(Long id);

    RentedCar saveRentedCar(RentedCar car);

    void deleteRentedCar(RentedCar car);

    RentedCar rentCar(Car car, Client client);

    RentedCar returnCar(Car car, Client client);
}
