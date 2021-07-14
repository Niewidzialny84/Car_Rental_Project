package niewidzialny84.github.rental.service;

import niewidzialny84.github.rental.entity.Car;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CarServiceTest extends ServiceAbstractTest {
    private final CarService service = new CarService(entityManager);

    @Test
    public void getAll() {
        var ref = new Object() {
            List<Car> l;
        };
        assertDoesNotThrow(()-> ref.l = service.getAllCars());
        assertFalse(ref.l.isEmpty());
    }

    @Test
    public void getFromId() {
        var car = service.getCarById(1L);
        assertNotNull(car);
        assertNotNull(car.getId());
        assertNotNull(car.getBrand());
        assertNotNull(car.getModel());
    }

    @Test
    public void getFromIdFail() {
        var car = service.getCarById(-1L);
        assertNull(car);
    }

    @Test
    public void saveNew() {
        var newCar = new Car("BMW","Strong");
        var ret = service.saveCar(newCar);
        assertNotNull(ret);
        assertNotNull(ret.getId());
        assertNotNull(ret.getBrand());
        assertNotNull(ret.getModel());
        assertEquals(newCar,ret);
    }

    @ParameterizedTest
    @CsvSource({"BMW,ABC","Mazda,BCD","Fast Car,X","Car,VeryFast","Wagon,Slow","Ferrari,Pegasus","Good,Meme"})
    public void saveModified(String brand,String model) {
        var car = service.getCarById(1L);
        assertNotNull(car);
        assertNotNull(car.getId());
        assertNotNull(car.getBrand());
        assertNotNull(car.getModel());

        var id = car.getId();
        car.setModel(model);
        car.setBrand(brand);
        var ret = service.saveCar(car);
        assertNotNull(ret);
        assertNotNull(ret.getId());
        assertNotNull(ret.getBrand());
        assertNotNull(ret.getModel());
        assertEquals(car,ret);
        assertEquals(id,ret.getId());
        assertEquals(model,ret.getModel());
        assertEquals(brand,ret.getBrand());
    }

    @Test
    public void deleteCar() {
        var newCar = new Car("BMW","Strong");
        var c = service.saveCar(newCar);

        assertNotNull(c);
        assertNotNull(newCar);
        assertEquals(newCar,c);
        assertEquals(newCar.getId(),c.getId());

        assertDoesNotThrow(() -> service.deleteCar(c));
        var car = service.getCarById(newCar.getId());
        assertNull(car);
        assertEquals(newCar,c);
    }
}
