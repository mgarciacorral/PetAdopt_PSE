
package es.uva.petadopt.rest;

import es.uva.petadopt.model.Cliente;
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
@Path("clientes")  // Cambié la ruta a clientes para mayor claridad
public class ClienteFacadeREST extends AbstractFacade<Cliente> {

    @PersistenceContext(unitName = "PetAdoptPU")
    private EntityManager em;

    public ClienteFacadeREST() {
        super(Cliente.class);
    }

    // Método para crear un cliente
    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void createCliente(Cliente cliente) {
        super.create(cliente);
    }

    // Método para buscar cliente por email
    @GET
    @Path("email/{email}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Cliente findByEmail(@PathParam("email") String email) {
        return em.find(Cliente.class, email);  // Buscamos al cliente por su email
    }

    @PUT
    @Path("{email}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("email") String email, Cliente cliente) {
        // Aquí se asume que el email es el identificador único para editar un cliente
        Cliente existingCliente = em.find(Cliente.class, email);
        if (existingCliente != null) {
            em.merge(cliente);  // Actualizamos el cliente
        }
    }

    @DELETE
    @Path("{email}")
    public void remove(@PathParam("email") String email) {
        Cliente cliente = em.find(Cliente.class, email);
        if (cliente != null) {
            em.remove(cliente);  // Eliminamos el cliente por su email
        }
    }

    @GET
    @Path("{email}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Cliente find(@PathParam("email") String email) {
        return em.find(Cliente.class, email);  // Buscamos el cliente por su email
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Cliente> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Cliente> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
