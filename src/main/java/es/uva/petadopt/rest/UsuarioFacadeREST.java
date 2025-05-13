
package es.uva.petadopt.rest;

import es.uva.petadopt.model.Usuario;
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
import org.springframework.security.crypto.bcrypt.BCrypt;


@Stateless
@Path("usuarios")
public class UsuarioFacadeREST extends AbstractFacade<Usuario> {

    @PersistenceContext(unitName = "PetAdoptPU")
    private EntityManager em;

    public UsuarioFacadeREST() {
        super(Usuario.class);
    }

    // Crear un usuario nuevo (con la contraseña cifrada)
    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Usuario entity) {
        // Ciframos la contraseña antes de guardarla
        String hashedPassword = BCrypt.hashpw(entity.getPassword(), BCrypt.gensalt());
        entity.setPassword(hashedPassword);
        super.create(entity);
    }

    // Método para verificar si una contraseña es correcta (compara la contraseña ingresada con la almacenada)
    @GET
    @Path("check-password/{email}/{rawPassword}")
    @Produces(MediaType.TEXT_PLAIN)
    public boolean checkPassword(@PathParam("email") String email, @PathParam("rawPassword") String rawPassword) {
        Usuario usuario = super.find(email);  // Busca el usuario por el email
        if (usuario != null) {
            return BCrypt.checkpw(rawPassword, usuario.getPassword());  // Compara la contraseña
        }
        return false;
    }

    // Cambiar la contraseña de un usuario mediante su email
    @PUT
    @Path("change-password/{email}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces(MediaType.TEXT_PLAIN)
    public boolean changePassword(@PathParam("email") String email, String newPassword) {
        try {
            Usuario usuario = super.find(email);  // Busca el usuario por el email
            if (usuario == null) {
                return false;  // Si no existe el usuario, devuelve false
            }
            // Cifra la nueva contraseña
            String hashedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
            usuario.setPassword(hashedPassword);
            super.edit(usuario);  // Actualiza el usuario con la nueva contraseña
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;  // Si ocurre algún error, devuelve false
        }
    }

    // Método para editar los detalles del usuario (se puede incluir la modificación de la contraseña aquí)
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") String id, Usuario entity) {
        super.edit(entity);
    }

    // Eliminar un usuario
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") String id) {
        super.remove(super.find(id));
    }

    // Buscar un usuario por su email
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Usuario find(@PathParam("id") String id) {
        return super.find(id);
    }

    // Obtener todos los usuarios
    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Usuario> findAll() {
        return super.findAll();
    }

    // Obtener un rango de usuarios
    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Usuario> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    // Contar la cantidad total de usuarios
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
