package niewidzialny84.github.rental.entity;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "rentedcars")
public class RentedCar {
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

    public RentedCar() {
    }

    public RentedCar(Car car, Client client) {
        this.car = car;
        this.client = client;
    }

    public RentedCar(Long id, Car car, Client client) {
        this.id = id;
        this.car = car;
        this.client = client;
    }

    public RentedCar(Long id, Car car, Client client, Date returnDate) {
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


    public void setReturnDate() {
        this.returnDate = new Date(System.currentTimeMillis());
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        return "RentedCar{" +
                "id=" + id +
                ", car=" + car +
                ", client=" + client +
                ", rentalDate=" + rentalDate +
                ", returnDate=" + returnDate +
                '}';
    }
}
