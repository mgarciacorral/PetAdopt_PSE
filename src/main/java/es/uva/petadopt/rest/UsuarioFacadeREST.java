
package es.uva.petadopt.rest;

import es.uva.petadopt.model.Usuario;
import java.security.MessageDigest;
import java.util.Base64;
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

    @GET
    @Path("check-password/{email}/{rawPassword}")
    @Produces(MediaType.TEXT_PLAIN)
    public boolean checkPassword(@PathParam("email") String email, @PathParam("rawPassword") String rawPassword) {
        Usuario usuario = super.find(email);  // Busca el usuario por el email
        if (usuario != null) {
            return hashPassword(rawPassword).equals(usuario.getPassword());  // Compara la contraseña
        }
        return false;
    }
    
    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = digest.digest(password.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error al encriptar la contraseña", e);
        }
    }

    // Cambiar la contraseña de un usuario mediante su email
    @PUT
    @Path("change-password/{email}")
    @Consumes({MediaType.TEXT_PLAIN})
    @Produces(MediaType.TEXT_PLAIN)
    public boolean changePassword(@PathParam("email") String email, String newPassword) {
        try {
            Usuario usuario = super.find(email); 
            if (usuario == null) {
                return false;  
            }
            
            String hashedPassword = hashPassword(newPassword);
            usuario.setPassword(hashedPassword);
            super.edit(usuario); 
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
