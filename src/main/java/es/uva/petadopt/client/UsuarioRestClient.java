
package es.uva.petadopt.client;

import es.uva.petadopt.model.Usuario;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


public class UsuarioRestClient {

    private static final String BASE_URL = "http://localhost:8080/PetAdopt_PSE/webresources/usuarios";
    private Client client;
    private WebTarget webTarget;
    
    public UsuarioRestClient(){
        client = ClientBuilder.newClient();
        webTarget = client.target(BASE_URL);
    }
    
    
    public void createUsuario(Usuario usuario) {
        Response response = webTarget
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(usuario, MediaType.APPLICATION_JSON));
    }

    public Usuario findByEmail(String email) {
        return client
                .target(BASE_URL)
                .path(email)
                .request(MediaType.APPLICATION_JSON)
                .get(Usuario.class);
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

    public void close() {
        client.close();
    }
}
