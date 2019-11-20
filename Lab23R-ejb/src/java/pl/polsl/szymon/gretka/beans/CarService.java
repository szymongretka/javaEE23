/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.szymon.gretka.beans;

import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.polsl.szymon.gretka.entity.Car;

/**
 *
 * @author Szymek
 */
@Stateless
@LocalBean
public class CarService {

    @PersistenceContext
    EntityManager em;
    
    public void createCar(Car car) {
        em.persist(car);
    }
    
    public Car getCarById(Long id) {
        return em.find(Car.class, id);
    }
    
    public List<Car> getAllCars() {
        return em.createQuery("Select c From Car c").getResultList();
    }
    
    public void updateCar(Long id, Car car) {
        Car carToUpdate = em.find(Car.class, id);
        carToUpdate.setBrand(car.getBrand());
        carToUpdate.setCarShowroom(car.getCarShowroom());
        carToUpdate.setColour(car.getColour());
        carToUpdate.setModel(car.getModel());
        carToUpdate.setYear(car.getYear());
        em.merge(carToUpdate);
    }
    
    public void deleteCar(Long id) {
        em.remove(em.find(Car.class, id));
    }
    
}
