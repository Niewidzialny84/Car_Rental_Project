package niewidzialny84.github.rental.service;

import niewidzialny84.github.rental.entity.Car;
import niewidzialny84.github.rental.entity.CarTest;
import niewidzialny84.github.rental.entity.Client;
import niewidzialny84.github.rental.entity.RentedCar;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import javax.persistence.NoResultException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RentedCarServiceTest extends ServiceAbstractTest {
    private final RentedCarService service = new RentedCarService(entityManager);
    private final CarService carService = new CarService(entityManager);
    private final ClientService clientService = new ClientService(entityManager);

    @Test
    public void getAll() {
        var ref = new Object() {
            List<RentedCar> l;
        };
        assertDoesNotThrow(()-> ref.l = service.getAllRentedCars());
        assertFalse(ref.l.isEmpty());
    }

    @Test
    public void getFromId() {
        var car = service.getRentedCarById(1L);
        assertNotNull(car);
        assertNotNull(car.getId());
        assertNotNull(car.getClient());
        assertNotNull(car.getCar());
    }

    @Test
    public void getFromIdFail() {
        var car = service.getRentedCarById(-1L);
        assertNull(car);
    }

    @Test
    public void saveNew() {
        var c = carService.saveCar(new Car("BMW","X"));
        var cl = clientService.saveClient(new Client("John","Connor"));
        var car = new RentedCar(c,cl);
        var ret = service.saveRentedCar(car);
        assertNotNull(ret);
        assertNotNull(ret.getId());
        assertNotNull(ret.getCar());
        assertNotNull(ret.getClient());
        assertNotNull(ret.getRentalDate());
        assertEquals(car,ret);
        assertEquals(car.getId(),ret.getId());
        assertEquals(c,ret.getCar());
        assertEquals(cl,ret.getClient());
    }

    @ParameterizedTest
    @CsvSource({"John,Connor","Adam,Newman","Luis,Deck","Joe,Doe","Summer,Smith"})
    public void saveModified(String first,String last) {
        var car = service.getRentedCarById(1L);
        assertNotNull(car);
        assertNotNull(car.getId());
        assertNotNull(car.getCar());
        assertNotNull(car.getClient());
        assertNotNull(car.getRentalDate());

        var id = car.getId();
        car.getClient().setFirstName(first);
        car.getClient().setLastName(last);
        var ret = service.saveRentedCar(car);
        assertNotNull(ret);
        assertNotNull(ret.getId());
        assertNotNull(ret.getClient());
        assertNotNull(ret.getClient().getFirstName());
        assertNotNull(ret.getClient().getLastName());
        assertEquals(car,ret);
        assertEquals(id,ret.getId());
        assertEquals(first,ret.getClient().getFirstName());
        assertEquals(last,ret.getClient().getLastName());
    }

    @Test
    public void deleteCar() {
        var c = carService.saveCar(new Car("BMW","X"));
        var cl = clientService.saveClient(new Client("John","Connor"));
        var car = new RentedCar(c,cl);
        var ret = service.saveRentedCar(car);
        assertNotNull(ret);
        assertNotNull(ret.getId());
        assertNotNull(ret.getCar());
        assertNotNull(ret.getClient());
        assertNotNull(ret.getRentalDate());
        assertEquals(car,ret);
        assertEquals(car.getId(),ret.getId());

        assertDoesNotThrow(() -> service.deleteRentedCar(ret));
        var s = service.getRentedCarById(car.getId());
        assertNull(s);
        assertEquals(car,ret);
    }

    @Test
    public void rentCar() {
        var c = carService.saveCar(new Car("BMW","X"));
        var cl = clientService.saveClient(new Client("John","Connor"));

        var ret = service.rentCar(c,cl);

        assertNotNull(ret);
        assertNotNull(ret.getId());
        assertNotNull(ret.getCar());
        assertNotNull(ret.getClient());
        assertNotNull(ret.getRentalDate());
        assertNull(ret.getReturnDate());
        assertEquals(c.getId(),ret.getCar().getId());
        assertEquals(cl.getId(),ret.getClient().getId());
    }

    @Test
    public void returnCar() {
        var c = carService.saveCar(new Car("BMW","X"));
        var cl = clientService.saveClient(new Client("John","Connor"));

        var ret = service.rentCar(c,cl);

        assertNotNull(ret);
        assertNotNull(ret.getId());
        assertNotNull(ret.getCar());
        assertNotNull(ret.getClient());
        assertNotNull(ret.getRentalDate());
        assertNull(ret.getReturnDate());
        assertEquals(c.getId(),ret.getCar().getId());
        assertEquals(cl.getId(),ret.getClient().getId());

        var car = service.returnCar(ret.getId());

        assertNotNull(ret);
        assertNotNull(ret.getId());
        assertNotNull(ret.getCar());
        assertNotNull(ret.getClient());
        assertNotNull(ret.getRentalDate());
        assertNotNull(ret.getReturnDate());
        assertEquals(ret,car);
        assertEquals(c.getId(),ret.getCar().getId());
        assertEquals(cl.getId(),ret.getClient().getId());
    }

    @Test
    public void rentCarFail() {
        var c = carService.saveCar(new Car("BMW","X"));
        var cl = clientService.saveClient(new Client("John","Connor"));
        var cl2 = clientService.saveClient(new Client("Joe","All"));

        var ret = service.rentCar(c,cl);

        assertNotNull(ret);
        assertNotNull(ret.getId());
        assertNotNull(ret.getCar());
        assertNotNull(ret.getClient());
        assertNotNull(ret.getRentalDate());
        assertNull(ret.getReturnDate());
        assertEquals(c.getId(),ret.getCar().getId());
        assertEquals(cl.getId(),ret.getClient().getId());

        assertNotNull(cl2);
        assertNotNull(cl2.getId());
        var ex = assertThrows(NoResultException.class,() -> service.rentCar(c,cl2));
        assertEquals(("Car Already Rented"),ex.getMessage());
    }

    @Test
    public void returnCarFail() {
        assertThrows(NoResultException.class,() -> service.returnCar(-1L));
    }
}
