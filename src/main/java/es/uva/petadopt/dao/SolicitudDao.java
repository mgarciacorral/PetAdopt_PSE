
package es.uva.petadopt.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class SolicitudDao {
    
    
    @PersistenceContext(unitName = "PetAdoptPU")
    private EntityManager entityManager;
    
    
    public void createSolicitud(){
        
    }

    
}
