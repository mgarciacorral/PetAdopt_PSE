package es.uva.petadopt.dao;

import es.uva.petadopt.model.Cliente;
import java.io.Serializable;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Stateless
public class ClienteDao {

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
