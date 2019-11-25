package pl.polsl.szymon.gretka.beans;

import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.polsl.szymon.gretka.entity.CarShowroom;

/**
 * 
 * @author Szymon Gretka
 * @version 1.0
 */
@Stateless
@LocalBean
public class CarShowroomService {

    /**
     * Entity manager used for CRUD operations
     */
   @PersistenceContext
    EntityManager em;
    
   /**
     * Creates new carshowroom in database
     * @param carShowroom  
     */
    public void createCarshowroom(CarShowroom carShowroom) {
        em.persist(carShowroom);
    }
    
    /**
     * finds carshowroom with given id
     * @param id
     * @return found carshowroom
     */
    public CarShowroom getCarshowroomById(Long id) {
        return em.find(CarShowroom.class, id);
    }
    
    /**
     * Finds list of all carshowrooms
     * @return all carshowrooms
     */
    public List<CarShowroom> getAllCarShowrooms() {
        return em.createQuery("Select c From CarShowroom c").getResultList();
    }
    
    /**
     * Updates carshowroom with given id
     * @param id
     * @param carShowroom 
     */
    public void updateCarShowroom(Long id, CarShowroom carShowroom) {
        CarShowroom carshowroomToUpdate = em.find(CarShowroom.class, id);
        carshowroomToUpdate.setName(carShowroom.getName());
        carshowroomToUpdate.setStreet(carShowroom.getStreet());
        carshowroomToUpdate.setCity(carShowroom.getCity());
        em.merge(carshowroomToUpdate);
    }
    
    /**
     * deletes carshowroom with given id
     * @param id 
     */
    public void deleteCarshowroom(Long id) {
        em.remove(em.find(CarShowroom.class, id));
    }
    
}
