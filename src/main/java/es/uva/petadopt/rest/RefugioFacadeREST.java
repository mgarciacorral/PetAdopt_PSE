
package es.uva.petadopt.rest;

import es.uva.petadopt.model.Refugio;
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
@Path("refugios")  // Ruta para los refugios
public class RefugioFacadeREST extends AbstractFacade<Refugio> {

    @PersistenceContext(unitName = "PetAdoptPU")
    private EntityManager em;

    public RefugioFacadeREST() {
        super(Refugio.class);
    }

    // Método para crear un refugio
    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void createRefugio(Refugio refugio) {
        em.persist(refugio);  // Persistimos el refugio
    }

    // Método para buscar refugio por email
    @GET
    @Path("email/{email}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Refugio findByEmail(@PathParam("email") String email) {
        return em.find(Refugio.class, email);  // Buscamos el refugio por su email
    }

    @PUT
    @Path("{email}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("email") String email, Refugio refugio) {
        // Aquí se asume que el email es el identificador único para editar un refugio
        Refugio existingRefugio = em.find(Refugio.class, email);
        if (existingRefugio != null) {
            em.merge(refugio);  // Actualizamos el refugio
        }
    }

    @DELETE
    @Path("{email}")
    public void remove(@PathParam("email") String email) {
        Refugio refugio = em.find(Refugio.class, email);
        if (refugio != null) {
            em.remove(refugio);  // Eliminamos el refugio por su email
        }
    }

    @GET
    @Path("{email}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Refugio find(@PathParam("email") String email) {
        return em.find(Refugio.class, email);  // Buscamos el refugio por su email
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Refugio> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Refugio> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
