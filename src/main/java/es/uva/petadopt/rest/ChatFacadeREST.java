
package es.uva.petadopt.rest;

import es.uva.petadopt.dto.ChatDTO;
import es.uva.petadopt.model.Chat;
import es.uva.petadopt.model.Cliente;
import es.uva.petadopt.model.Refugio;
import es.uva.petadopt.model.Solicitudadopcion;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Stateless
@Path("chats")  
public class ChatFacadeREST extends AbstractFacade<Chat> {

    @PersistenceContext(unitName = "PetAdoptPU")
    private EntityManager em;

    public ChatFacadeREST() {
        super(Chat.class);
    }

     @POST
    @Path("create")
    public Response createChat(ChatDTO dto) {
        try {
            Cliente cliente = em.find(Cliente.class, dto.getClienteId());
            Refugio refugio = em.find(Refugio.class, dto.getRefugioId());
            Solicitudadopcion solicitud = em.find(Solicitudadopcion.class, dto.getSolicitudId());

            if (cliente == null || refugio == null || solicitud == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Datos inválidos").build();
            }

            Chat chat = new Chat();
            chat.setEmailCliente(cliente.getEmail());
            chat.setEmailRefugio(refugio.getEmail());
            chat.setIdSolicitud(solicitud.getIdSolicitud());

            em.persist(chat);

            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().build();
        }
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
    
    @GET
    @Path("between/{emailCliente}/{emailRefugio}")
    @Produces(MediaType.APPLICATION_JSON)
    public Integer findChat(@PathParam("emailCliente") String emailCliente,
            @PathParam("emailRefugio") String emailRefugio) {

        try {
            TypedQuery<Integer> query = em.createQuery(
                    "SELECT c.idChat FROM Chat c WHERE c.emailCliente = :emailCliente AND c.emailRefugio = :emailRefugio",
                    Integer.class
            );
            query.setParameter("emailCliente", emailCliente);
            query.setParameter("emailRefugio", emailRefugio);

            return query.getSingleResult(); 
        } catch (NoResultException e) {
            throw new WebApplicationException("Chat no encontrado", 404);
        }
    }

    
    @GET
    @Path("por-solicitud/{idSolicitud}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Chat> findByMascota(@PathParam("idMascota") Integer idMascota) {
        return getEntityManager()
                .createQuery("SELECT s FROM Chat s WHERE s.idChat = :id", Chat.class)
                .setParameter("id", idMascota)
                .getResultList();
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
