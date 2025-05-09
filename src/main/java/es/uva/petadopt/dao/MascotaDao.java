package es.uva.petadopt.dao;

import es.uva.petadopt.model.Mascota;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class MascotaDao implements Serializable{

    @PersistenceContext
    private EntityManager entityManager;

    // Buscar todas las mascotas
    public List<Mascota> findAll() {
        Query query = entityManager.createQuery("SELECT m FROM Mascota m");
        return query.getResultList();
    }

    // Buscar mascotas por especie
    public List<Mascota> findByEspecie(String especie) {
        Query query = entityManager.createQuery("SELECT m FROM Mascota m WHERE m.especie = :especie");
        query.setParameter("especie", especie);
        return query.getResultList();
    }

    // Buscar mascotas por raza
    public List<Mascota> findByRaza(String raza) {
        Query query = entityManager.createQuery("SELECT m FROM Mascota m WHERE m.raza = :raza");
        query.setParameter("raza", raza);
        return query.getResultList();
    }
}
