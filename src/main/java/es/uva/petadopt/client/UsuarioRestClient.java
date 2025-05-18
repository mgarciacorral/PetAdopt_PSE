package es.uva.petadopt.client;

import es.uva.petadopt.model.Usuario;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class UsuarioRestClient {

    private static final String BASE_URL = "http://localhost:8080/PetAdopt_PSE/webresources/usuarios";
    private final Client client;
    private final WebTarget webTarget;
    
    public UsuarioRestClient(){
        client = ClientBuilder.newClient();
        webTarget = client.target(BASE_URL);
    }

    public Usuario findByEmail(String email) {
        return client
                .target(BASE_URL)
                .path(email)
                .request(MediaType.APPLICATION_JSON)
                .get(Usuario.class);
    }
    
    public void update(Usuario usuario){
        webTarget.path(usuario.getEmail())
                .request()
                .put(Entity.entity(usuario, MediaType.APPLICATION_JSON));
    }
    
    public List<Usuario> findAll() {
        Response response = webTarget
                .request(MediaType.APPLICATION_JSON)
                .get();

        if (response.getStatus() != 200) {
            throw new RuntimeException("Error al obtener todas las mascotas: " + response.getStatus());
        }

        return response.readEntity(new GenericType<List<Usuario>>() {
        });
    }
    
    public boolean checkPassword(String email, String rawPassword) {
        String response = webTarget
                .path("check-password")
                .path(email)
                .path(rawPassword)
                .request(MediaType.TEXT_PLAIN)
                .get(String.class); 

        return Boolean.parseBoolean(response);
    }

    public boolean changePassword(String email, String newPassword) {
    String response = webTarget
            .path("change-password")
            .path(email)
            .request(MediaType.TEXT_PLAIN)
            .put(Entity.entity(newPassword, MediaType.TEXT_PLAIN), String.class);

        return Boolean.parseBoolean(response);
    }
    
    public void delete(Usuario user){
        Response response = webTarget.path(user.getEmail()).request().delete();
        client.target("http://localhost:8080/PetAdopt_PSE/webresources/es.uva.petadopt.model.usergroups").path(user.getEmail()).request("text/plain").delete();

        if (response.getStatus() == 204) {
            System.out.println("Usuario eliminado correctamente.");
            if(user.getTipo().equals("cliente")){
                new ClienteRestClient().delete(user.getEmail());
            }else{
                new RefugioRestClient().delete(user.getEmail());
            }
        } else {
            System.out.println("Error al eliminar la solicitud. Código: " + response.getStatus());
        }

        response.close();
    }

    public void close() {
        client.close();
    }
}
