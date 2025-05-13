
package es.uva.petadopt.client;

import es.uva.petadopt.model.Refugio;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


public class RefugioRestClient {
    private static final String BASE_URL = "http://localhost:8080/PetAdopt_PSE/webresources/refugios";
    private Client client;
    private WebTarget webTarget;

    public RefugioRestClient() {
        client = ClientBuilder.newClient();
        webTarget = client.target(BASE_URL);
    }
    
    public void createRefugio(Refugio refugio) {
        Response response = webTarget
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(refugio, MediaType.APPLICATION_JSON));
    } 
    
    public Refugio findByEmail(String email) {
        return client
                .target(BASE_URL)
                .path(email)
                .request(MediaType.APPLICATION_JSON)
                .get(Refugio.class);
    }
    
    public void close() {
        client.close();
    }
}
