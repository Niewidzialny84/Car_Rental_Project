package niewidzialny84.github.rental.entity;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RentedCarTest {
    private final Long id = 1L;
    private final Car car = new Car(id,"BMW","Model X");
    private final Client client = new Client(id,"John","Connor");
    private final Date rentDate = new Date(System.currentTimeMillis());
    private final RentedCar rentedCar = new RentedCar(id,car,client,rentDate,rentDate);

    @Test
    public void getRentedCarId() {
        assertEquals(id,rentedCar.getId());
    }

    @Test
    public void setRentedCarId() {
        rentedCar.setId(5L);
        assertEquals(5L,rentedCar.getId());
    }

    @Test
    public void getCar() {
        assertEquals(car,rentedCar.getCar());
    }

    @Test
    public void setCar() {
        var c = new Car(2L,"ABC","X");
        rentedCar.setCar(c);
        assertEquals(c,rentedCar.getCar());
    }

    @Test
    public void getClient() {
        assertEquals(client,rentedCar.getClient());
    }

    @Test
    public void setClient() {
        var c = new Client(2L,"Dolores","Umbridge");
        rentedCar.setClient(c);
        assertEquals(c,rentedCar.getClient());
    }

    @Test
    public void setReturnDateSelf() {
        var c = new Date(System.currentTimeMillis());
        rentedCar.setReturnDate();
        assertEquals(c,rentedCar.getReturnDate());
    }

    @Test
    public void setReturnDate() {
        rentedCar.setReturnDate(rentDate);
        assertEquals(rentDate,rentedCar.getReturnDate());
    }

    @Test
    public void setRentDate() {
        rentedCar.setRentalDate(rentDate);
        assertEquals(rentDate,rentedCar.getRentalDate());
    }

    @Test
    public void getRentalDate() {
        assertEquals(rentDate,rentedCar.getRentalDate());
    }
}
