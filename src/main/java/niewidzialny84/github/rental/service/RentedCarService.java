package niewidzialny84.github.rental.service;

import niewidzialny84.github.rental.entity.Car;
import niewidzialny84.github.rental.entity.Client;
import niewidzialny84.github.rental.entity.RentedCar;
import niewidzialny84.github.rental.repository.RentedCarRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class RentedCarService extends Service implements RentedCarRepository {
    public RentedCarService(EntityManager em) {
        super(em);
    }

    @Override
    public List<RentedCar> getAllRentedCars() {
        return em.createQuery("SELECT x FROM RentedCar x", RentedCar.class).getResultList();
    }

    @Override
    public RentedCar getRentedCarById(Long id) {
        return em.find(RentedCar.class,id);
    }

    @Override
    public RentedCar saveRentedCar(RentedCar car) {
        if (car.getId() == null) {
            em.persist(car);
        } else {
            car = em.merge(car);
        }
        return car;
    }

    @Override
    public void deleteRentedCar(RentedCar car) {
        if (em.contains(car)) {
            em.remove(car);
        } else {
            em.merge(car);
        }
    }

    @Override
    public RentedCar rentCar(Car car, Client client) throws NoResultException {
        TypedQuery<RentedCar> q = em.createQuery("SELECT x FROM RentedCar x WHERE x.car.id=:car AND x.returnDate=NULL",RentedCar.class);
        q.setParameter("car",car.getId());
        List<RentedCar> r = q.getResultList();
        if(r.size() > 0) {
            throw new NoResultException("Car Already Rented");
        }
        return saveRentedCar(new RentedCar(car,client));
    }

    @Override
    public RentedCar returnCar(Long id) throws NoResultException{
        var car = getRentedCarById(id);

        if(car == null) {
            throw new NoResultException("Record Not Found");
        }

        car.setReturnDate();

        return saveRentedCar(car);
    }
}
