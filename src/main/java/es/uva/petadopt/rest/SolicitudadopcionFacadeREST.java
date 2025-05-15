
package es.uva.petadopt.rest;

import es.uva.petadopt.dto.SolicitudDTO;
import es.uva.petadopt.model.Cliente;
import es.uva.petadopt.model.Mascota;
import es.uva.petadopt.model.Solicitudadopcion;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;


@Stateless
@Path("solicitudes")
public class SolicitudadopcionFacadeREST extends AbstractFacade<Solicitudadopcion> {

    @PersistenceContext(unitName = "PetAdoptPU")
    private EntityManager em;

    public SolicitudadopcionFacadeREST() {
        super(Solicitudadopcion.class);
    }

    // Método para crear una nueva solicitud de adopción
@POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public void createSolicitud(SolicitudDTO dto) {
        Solicitudadopcion solicitud = new Solicitudadopcion();
        solicitud.setEstado("pendiente");
        solicitud.setIdMascota(dto.getMascota().getIdMascota());
        solicitud.setEmailCliente(dto.getCliente().getEmail());

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        solicitud.setFechaSolicitud(cal.getTime());

        em.persist(solicitud);
    }


    // Método para comprobar si ya existe una solicitud de adopción para un cliente y mascota
    @GET
    @Path("comprobar/{emailCliente}/{idMascota}")
    @Produces(MediaType.TEXT_PLAIN)
    public boolean comprobarSolicitud(@PathParam("emailCliente") String emailCliente, @PathParam("idMascota") int idMascota) {


        // Consultamos las solicitudes existentes para este cliente y mascota
        List<Solicitudadopcion> solicitudes = em.createQuery(
                "SELECT s FROM Solicitudadopcion s WHERE s.emailCliente = :cliente AND s.idMascota = :mascota", Solicitudadopcion.class)
                .setParameter("cliente", emailCliente)
                .setParameter("mascota", idMascota)
                .getResultList();

        return solicitudes.isEmpty();  // Si está vacía, no existe una solicitud
    }

    // Método para obtener todas las mascotas solicitadas por un cliente
    @GET
    @Path("solicitadas/{emailCliente}")
    @Produces({ MediaType.APPLICATION_JSON})
    public List<Mascota> findSolicitadas(@PathParam("emailCliente") String emailCliente) {
        Cliente cliente = em.find(Cliente.class, emailCliente);

        if (cliente == null) {
            throw new WebApplicationException("Cliente no encontrado", 404);
        }

        List<Integer> ids = em.createQuery(
                "SELECT s.idMascota FROM Solicitudadopcion s WHERE s.emailCliente = :emailCliente", Integer.class)
                .setParameter("emailCliente", emailCliente)
                .getResultList();
        
        List<Mascota> mascotas = ids.stream()
                .map(id -> em.find(Mascota.class, id))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        
        return mascotas;
        
    }

    
    

    // Método para obtener la última solicitud de adopción de un cliente para una mascota
    @GET
    @Path("ultima/{emailCliente}/{idMascota}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Solicitudadopcion getLastSolicitudId(@PathParam("emailCliente") String emailCliente, @PathParam("idMascota") int idMascota) {


        // Consultamos la última solicitud de adopción
        Solicitudadopcion solicitud = em.createQuery(
                "SELECT s FROM Solicitudadopcion s WHERE s.emailCliente = :cliente AND s.idMascota = :mascota ORDER BY s.idSolicitud DESC",
                Solicitudadopcion.class)
                .setParameter("cliente", emailCliente)
                .setParameter("mascota", idMascota)
                .setMaxResults(1)
                .getSingleResult();

        return solicitud;
    }

    // Métodos estándar de CRUD (find, findAll, findRange, count)
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Solicitudadopcion find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Solicitudadopcion> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Solicitudadopcion> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
