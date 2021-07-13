package niewidzialny84.github.rental.repository;

import niewidzialny84.github.rental.entity.Car;

import java.util.List;

public interface CarRepository{

    List<Car> getAllCars();

    Car getCarById(Long id);

    Car saveCar(Car car);

    void deleteCar(Car car);
}
