/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.szymon.gretka;

import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Szymek
 */
@Stateless
@LocalBean
public class NewSessionBean {

    @PersistenceContext
    EntityManager em;
    
    public int add(int parameter1, int parameter2) {
        return parameter1 + parameter2;
    }
    
    public List<Carr> getAllCars() {
        Carr car1 = new Carr("brand", "model", "colour", 2000);
        Carr car2 = new Carr("brand", "model", "colour", 2001);
        em.persist(car1);
        em.persist(car2);
        return em.createQuery("Select c From Car c").getResultList();
    }
    

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
