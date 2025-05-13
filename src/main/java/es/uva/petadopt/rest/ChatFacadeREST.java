
package es.uva.petadopt.rest;

import es.uva.petadopt.model.Chat;
import es.uva.petadopt.model.Cliente;
import es.uva.petadopt.model.Refugio;
import es.uva.petadopt.model.Solicitudadopcion;
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
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;


@Stateless
@Path("chats")  
public class ChatFacadeREST extends AbstractFacade<Chat> {

    @PersistenceContext(unitName = "PetAdoptPU")
    private EntityManager em;

    public ChatFacadeREST() {
        super(Chat.class);
    }

    // Método para crear un chat
    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void createChat(CreateChatRequest request) {
        Cliente cliente = em.find(Cliente.class, request.getClienteId());
        Refugio refugio = em.find(Refugio.class, request.getRefugioId());
        Solicitudadopcion solicitud = em.find(Solicitudadopcion.class, request.getSolicitudId());

        // Si alguno de los elementos no se encuentra, lanzamos un error
        if (cliente == null || refugio == null || solicitud == null) {
            throw new WebApplicationException("Datos inválidos para crear el chat", 400);
        }

        Chat chat = new Chat();
        chat.setEmailCliente(cliente);
        chat.setEmailRefugio(refugio);
        chat.setIdSolicitud(solicitud);

        em.persist(chat);
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Chat find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Chat> findAll() {
        return super.findAll();
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    // Clase auxiliar para recibir los datos de la solicitud
    public static class CreateChatRequest {

        private Integer clienteId;
        private Integer refugioId;
        private Integer solicitudId;

        // Getters y Setters
        public Integer getClienteId() {
            return clienteId;
        }

        public void setClienteId(Integer clienteId) {
            this.clienteId = clienteId;
        }

        public Integer getRefugioId() {
            return refugioId;
        }

        public void setRefugioId(Integer refugioId) {
            this.refugioId = refugioId;
        }

        public Integer getSolicitudId() {
            return solicitudId;
        }

        public void setSolicitudId(Integer solicitudId) {
            this.solicitudId = solicitudId;
        }
    }
}
