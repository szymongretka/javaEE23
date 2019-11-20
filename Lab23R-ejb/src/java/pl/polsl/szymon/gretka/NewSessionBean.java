package pl.polsl.szymon.gretka;

import pl.polsl.szymon.gretka.entity.Car;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Szymon Gretka
 */
@Stateless
@LocalBean
public class NewSessionBean {

    @PersistenceContext
    EntityManager em;
    
    public int add(int parameter1, int parameter2) {
        return parameter1 + parameter2;
    }
    
    public List<Car> getAllCars() {
        return em.createQuery("Select c From Car c").getResultList();
    }
    
    public Car getCarById(Long id) {
        return em.find(Car.class, id);
    }

}
