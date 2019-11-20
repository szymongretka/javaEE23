package pl.polsl.szymon.gretka.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Szymon Gretka
 */
@Entity
@Table(name = "carshowroom")
@NamedQueries({
    @NamedQuery(name = CarShowroom.FIND_ALL, 
            query = "SELECT c FROM CarShowroom c")
})
public class CarShowroom implements Serializable {
    
    /**
     * Simple query for finding all carshowrooms
     */
    public static final String FIND_ALL = "CarShowroom.findAll";
    
    
    @Id
    @Column(name = "carshowroom_id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name", nullable = false, length = 50)
    private String name;
    
    @Column(name = "city", nullable = false, length = 50)
    private String city;
    
    @Column(name = "street_number", nullable = false, length = 50)
    private String street;
    
    @OneToMany(mappedBy = "carShowroom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Car> cars = new ArrayList<>();
    
    /**
     * metohd used for adding a car to carshowroom
     * @param car
     */
    public void addCar(Car car) {
        cars.add(car);
        car.setCarShowroom(this);
    }
    
    /**
     * No args contructor
     */
    public CarShowroom() {}
    
    /**
     * Constructor for creating a new carshowroom.
     * @param name
     * @param city
     * @param street
     */
    public CarShowroom(String name, String city, String street) {
        this.name = name;
        this.city = city;
        this.street = street;
    }

    /**
     * Constructor for creating a new carshowroom.
     * @param name
     * @param city
     * @param street
     * @param cars
     */
    public CarShowroom(String name, String city, String street, List<Car> cars) {
        this.name = name;
        this.city = city;
        this.street = street;
        this.cars = cars;
    }   

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.id);
        hash = 23 * hash + Objects.hashCode(this.name);
        hash = 23 * hash + Objects.hashCode(this.city);
        hash = 23 * hash + Objects.hashCode(this.street);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CarShowroom other = (CarShowroom) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.city, other.city)) {
            return false;
        }
        if (!Objects.equals(this.street, other.street)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CarShowroom{" + "id=" + id + ", name=" + name + ", city=" 
                + city + ", street=" + street + '}';
    }
    
    
    
}
