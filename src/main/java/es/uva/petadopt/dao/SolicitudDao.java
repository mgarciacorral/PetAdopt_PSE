package es.uva.petadopt.dao;

import es.uva.petadopt.model.Cliente;
import es.uva.petadopt.model.Mascota;
import es.uva.petadopt.model.Solicitudadopcion;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
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


}