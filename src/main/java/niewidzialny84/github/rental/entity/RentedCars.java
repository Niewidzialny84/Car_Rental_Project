package niewidzialny84.github.rental.entity;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
public class RentedCars {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @ManyToOne
    @JoinColumn(name = "carID", referencedColumnName = "id")
    private Car car;

    @ManyToOne
    @JoinColumn(name = "clientID",referencedColumnName = "id")
    private Client client;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "rentalDate", nullable = false)
    @CreationTimestamp
    @ColumnDefault("CURRENT_TIMESTAMP")
    private Date rentalDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date returnDate;

    public RentedCars(Long id, Car car, Client client) {
        this.id = id;
        this.car = car;
        this.client = client;
    }

    public RentedCars(Long id, Car car, Client client, Date returnDate) {
        this.id = id;
        this.car = car;
        this.client = client;
        this.returnDate = returnDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Date getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(Date rentalDate) {
        this.rentalDate = rentalDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
}
