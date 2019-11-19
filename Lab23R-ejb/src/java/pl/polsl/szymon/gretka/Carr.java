package pl.polsl.szymon.gretka;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Car is an entity class which is being used for CRUD operations
 * 
 * @author Szymon Gretka
 */
@Entity
@Table(name = "car")
@NamedQueries({
    @NamedQuery(name = Carr.FIND_ALL, 
            query = "SELECT c FROM Car c")
})
public class Carr implements Serializable {
    
    /**
     * Simple query for finding all cars
     */
    public static final String FIND_ALL = "Car.findAll";

    @Id
    @Column(name = "car_id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "brand", nullable = false, length = 50)
    private String brand;
    
    @Column(name = "car_model", nullable = false, length = 50)
    private String model;
    
    @Column(name = "colour", nullable = false, length = 50)
    private String colour;
    
    @Column(name = "year_of_manufacture", nullable = false)
    private Integer year;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carshowroom_id")
    private CarrShowroom carShowroom;

    /**
     * No args contructor
     */
    public Carr(){}

    /**
     * Constructor for creating a new car.
     * @param brand is the brand of the car
     * @param model is the brand of the car
     * @param colour is the brand of the car
     * @param year is the brand of the car
     */
    public Carr(String brand, String model, String colour, Integer year) {
        this.brand = brand;
        this.model = model;
        this.colour = colour;
        this.year = year;
    }

    /**
     * Constructor for creating a new car.
     * @param id
     * @param brand
     * @param model
     * @param colour
     * @param year
     * @param carShowroom
     */
    public Carr(Long id, String brand, String model, String colour, Integer year, 
            CarrShowroom carShowroom) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.colour = colour;
        this.year = year;
        this.carShowroom = carShowroom;
    }
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public CarrShowroom getCarShowroom() {
        return carShowroom;
    }

    public void setCarShowroom(CarrShowroom carShowroom) {
        this.carShowroom = carShowroom;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.id);
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
        final Carr other = (Carr) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    

    

    @Override
    public String toString() {
        return "Car{" + "id=" + id + ", brand=" + brand + ", model=" + model 
                + ", colour=" + colour + ", year=" + year + '}';
    }
    
    
    
    
}
