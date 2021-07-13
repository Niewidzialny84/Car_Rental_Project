package niewidzialny84.github.rental.service;

import niewidzialny84.github.rental.entity.Car;
import niewidzialny84.github.rental.repository.CarRepository;

import javax.persistence.EntityManager;
import java.util.List;

public class CarService extends Service implements CarRepository {
    public CarService(EntityManager em) {
        super(em);
    }

    @Override
    public List<Car> getAllCars() {
        return em.createQuery("SELECT x FROM Car x",Car.class).getResultList();
    }

    @Override
    public Car getCarById(Long id) {
        return em.find(Car.class, id);
    }

    @Override
    public Car saveCar(Car car) {
        if (car.getId() == null) {
            em.persist(car);
        } else {
            car = em.merge(car);
        }
        return car;
    }

    @Override
    public void deleteCar(Car car) {
        if(em.contains(car)) {
            em.remove(car);
        } else {
            em.merge(car);
        }
    }
}
