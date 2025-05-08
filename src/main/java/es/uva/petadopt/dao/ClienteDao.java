package es.uva.petadopt.dao;

import es.uva.petadopt.model.Cliente;
import java.io.Serializable;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ClienteDao implements Serializable{

    @PersistenceContext(unitName = "PetAdoptPU")
    private EntityManager entityManager;

    // Guardar Cliente

    public void save(Cliente cliente) {
        entityManager.persist(cliente);
    }

    // Buscar Cliente por email
    public Cliente findByEmail(String email) {
        return entityManager.find(Cliente.class, email);
    }
}
