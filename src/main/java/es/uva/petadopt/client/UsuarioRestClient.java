package es.uva.petadopt.client;

import es.uva.petadopt.model.Usuario;
import java.security.MessageDigest;
import java.util.Base64;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class UsuarioRestClient {

    private static final String BASE_URL = "http://localhost:8080/PetAdopt_PSE/webresources/usuarios";
    private Client client;
    private WebTarget webTarget;
    
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

    public void close() {
        client.close();
    }
}
