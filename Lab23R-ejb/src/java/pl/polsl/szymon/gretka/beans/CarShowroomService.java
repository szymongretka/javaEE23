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
 */
@Stateless
@LocalBean
public class CarShowroomService {

   @PersistenceContext
    EntityManager em;
    
    public void createCarshowroom(CarShowroom carShowroom) {
        em.persist(carShowroom);
    }
    
    public CarShowroom getCarshowroomById(Long id) {
        return em.find(CarShowroom.class, id);
    }
    
    public List<CarShowroom> getAllCarShowrooms() {
        return em.createQuery("Select c From CarShowroom c").getResultList();
    }
    
    public void updateCarShowroom(Long id, CarShowroom carShowroom) {
        CarShowroom carshowroomToUpdate = em.find(CarShowroom.class, id);
        carshowroomToUpdate.setName(carShowroom.getName());
        carshowroomToUpdate.setStreet(carShowroom.getStreet());
        carshowroomToUpdate.setCity(carShowroom.getCity());
        em.merge(carshowroomToUpdate);
    }
    
    public void deleteCarshowroom(Long id) {
        em.remove(em.find(CarShowroom.class, id));
    }
    
}
