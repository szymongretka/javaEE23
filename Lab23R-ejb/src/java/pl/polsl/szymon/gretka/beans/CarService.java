package pl.polsl.szymon.gretka.beans;

import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.polsl.szymon.gretka.entity.Car;

/**
 *
 * @author Szymon Gretka
 * @version 1.0
 */
@Stateless
@LocalBean
public class CarService {

    /**
     * Entity manager used for CRUD operations
     */
    @PersistenceContext
    EntityManager em;
    
    /**
     * Creates new car in database
     * @param car 
     */
    public void createCar(Car car) {
        em.persist(car);
    }
    
    /**
     * Finds car with given id
     * @param id
     * @return found car
     */
    public Car getCarById(Long id) {
        return em.find(Car.class, id);
    }
    
    /**
     * Finds all cars
     * @return list of cars
     */
    public List<Car> getAllCars() {
        return em.createQuery("Select c From Car c").getResultList();
    }
    
    /**
     * updates car with given id
     * @param id
     * @param car 
     */
    public void updateCar(Long id, Car car) {
        Car carToUpdate = em.find(Car.class, id);
        carToUpdate.setBrand(car.getBrand());
        carToUpdate.setCarShowroom(car.getCarShowroom());
        carToUpdate.setColour(car.getColour());
        carToUpdate.setModel(car.getModel());
        carToUpdate.setYear(car.getYear());
        em.merge(carToUpdate);
    }
    
    /**
     * deletes car with given id
     * @param id 
     */
    public void deleteCar(Long id) {
        em.remove(em.find(Car.class, id));
    }
    
}
