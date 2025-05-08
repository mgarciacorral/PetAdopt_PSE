package es.uva.petadopt.dao;

import es.uva.petadopt.model.Refugio;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class RefugioDao {

    @PersistenceContext(unitName = "PetAdoptPU")
    private EntityManager entityManager;


    public void save(Refugio refugio) {
        entityManager.persist(refugio);

    }

    // Buscar Refugio por email
    public Refugio findByEmail(String email) {
        return entityManager.find(Refugio.class, email);
    }
}