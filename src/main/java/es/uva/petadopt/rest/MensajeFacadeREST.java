
package es.uva.petadopt.rest;

import es.uva.petadopt.model.Chat;
import es.uva.petadopt.model.Mensaje;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Stateless
@Path("mensaje")
public class MensajeFacadeREST extends AbstractFacade<Mensaje> {

    @PersistenceContext(unitName = "PetAdoptPU")
    private EntityManager em;

    public MensajeFacadeREST() {
        super(Mensaje.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Mensaje entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Mensaje entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Mensaje find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Mensaje> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Mensaje> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }
    
    @GET
    @Path("mensajes/cliente/{idChat}/{emailCliente}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Mensaje> findMensajesCliente(@PathParam("idChat") Integer idChat, @PathParam("emailCliente") String emailCliente) {
        return getEntityManager()
                .createQuery("SELECT m FROM Mensaje m WHERE m.idChat = :id AND m.remitente = :emailCliente", Mensaje.class)
                .setParameter("id", idChat)
                .setParameter("emailCliente", emailCliente)
                .getResultList();
    }
    
    @GET
    @Path("mensajes/refugio/{idChat}/{emailRefugio}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Mensaje> findMensajesRefugio(@PathParam("idChat") Integer idChat, @PathParam("emailRefugio") String emailRefugio) {
        return getEntityManager()
                .createQuery("SELECT m FROM Mensaje m WHERE m.idChat = :id AND m.remitente = :emailRefugio", Mensaje.class)
                .setParameter("id", idChat)
                .setParameter("emailRefugio", emailRefugio)
                .getResultList();
    }
    
    @GET
    @Path("mensajes/{idChat}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Mensaje> findMensajesByChat(@PathParam("idChat") Integer idChat) {
        return getEntityManager()
                .createQuery("SELECT m FROM Mensaje m WHERE m.idChat = :id ", Mensaje.class)
                .setParameter("id", idChat)
                .getResultList();
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
