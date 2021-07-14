package niewidzialny84.github.rental.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class CarTest {
    private final Long id = 1L;
    private final String brand = "BWM";
    private final String model = "model X";
    private final Car car = new Car(id,brand,model);

    @Test
    public void getCarId() {
        assertEquals(id,car.getId());
    }

    @Test
    public void setCarId() {
        car.setId(5L);
        assertEquals(5L,car.getId());
    }

    @Test
    void getBrand() {
        assertEquals(brand, car.getBrand());
    }

    @Test
    void setBrand() {
        car.setBrand("BMW");
        assertEquals("BMW", car.getBrand());
    }

    @Test
    void getModel() {
        assertEquals(model, car.getModel());
    }

    @Test
    void setModel() {
        car.setModel("MX");
        assertEquals("MX", car.getModel());
    }

}
