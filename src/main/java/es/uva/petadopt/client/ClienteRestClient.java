
package es.uva.petadopt.client;

import es.uva.petadopt.model.Cliente;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


public class ClienteRestClient {
    
    private static final String BASE_URL = "http://localhost:8080/PetAdopt_PSE/webresources/clientes";
    private Client client;
    private WebTarget webTarget;
    
    
    public ClienteRestClient(){
        client = ClientBuilder.newClient();
        webTarget = client.target(BASE_URL);
    }
    
    public void createCliente(Cliente cliente) {
        Response response = webTarget
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(cliente, MediaType.APPLICATION_JSON));
    }
    
    public Cliente findByEmail(String email){
        return client
                .target(BASE_URL)
                .path(email)
                .request(MediaType.APPLICATION_JSON)
                .get(Cliente.class);
    }
    
}
