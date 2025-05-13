package es.uva.petadopt.dao;


import es.uva.petadopt.model.Cliente;
import es.uva.petadopt.model.Mascota;
import es.uva.petadopt.model.Solicitudadopcion;


import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class SolicitudDao {
    
    
    @PersistenceContext(unitName = "PetAdoptPU")
    private EntityManager entityManager;
    
    
    public void createSolicitud(Cliente emailUser, Mascota idMascota){
        
        Solicitudadopcion solicitud = new Solicitudadopcion();
        
        solicitud.setEmailCliente(emailUser);
        solicitud.setIdMascota(idMascota);
        solicitud.setEstado("pendiente");
        
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        Date soloFecha = cal.getTime();
        
        solicitud.setFechaSolicitud(soloFecha);

        entityManager.persist(solicitud);
        
    }   
    
    public boolean comprobarSolicitud(Cliente cliente, Mascota mascota){
        
        List<Solicitudadopcion> solicitudes = entityManager.createQuery("SELECT s FROM Solicitudadopcion s WHERE s.emailCliente = :cliente AND s.idMascota = :mascota")
                .setParameter("cliente", cliente)
                .setParameter("mascota", mascota)
                .getResultList();
        
        return solicitudes.isEmpty();
    }
    
    public List<Mascota> findSolicitadas(Cliente cliente){
        Query query = entityManager.createQuery("SELECT s.idMascota FROM Solicitudadopcion s WHERE s.emailCliente = :cliente");
        query.setParameter("cliente", cliente);
        return query.getResultList();
    }
    
    public Solicitudadopcion getLastSolicitudId(Cliente cliente, Mascota mascota) {
        
            Solicitudadopcion solicitud = entityManager.createQuery(
                    "SELECT s FROM Solicitudadopcion s WHERE s.emailCliente = :cliente AND s.idMascota = :mascota ORDER BY s.idSolicitud DESC",
                    Solicitudadopcion.class)
                    .setParameter("cliente", cliente)
                    .setParameter("mascota", mascota)
                    .setMaxResults(1)
                    .getSingleResult();

            return solicitud;
        
    }

}