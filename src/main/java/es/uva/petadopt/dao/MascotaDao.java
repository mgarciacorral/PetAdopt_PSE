package es.uva.petadopt.dao;

import es.uva.petadopt.model.Mascota;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

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
    
    public  List<String> obtenerEspecies(){
        TypedQuery<String> query = entityManager.createQuery("SELECT DISTINCT m.especie FROM Mascota m", String.class);
        return query.getResultList();
    }
    
    public  List<String> obtenerRazasPorEspecie(String especie){
        TypedQuery<String> query = entityManager.createQuery("SELECT DISTINCT m.raza FROM Mascota m WHERE m.especie = :especie", String.class);
        query.setParameter("especie", especie);
        return query.getResultList();
    }
}
