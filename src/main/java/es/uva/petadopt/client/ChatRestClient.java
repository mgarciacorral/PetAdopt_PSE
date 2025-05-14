
package es.uva.petadopt.client;

import es.uva.petadopt.model.Cliente;
import es.uva.petadopt.model.Refugio;
import es.uva.petadopt.model.Solicitudadopcion;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

public class ChatRestClient {
    
    private static final String BASE_URL = "http://localhost:8080/PetAdopt_PSE/webresources/chats";
    private Client client;
    private WebTarget webTarget;

    public ChatRestClient() {
        client = ClientBuilder.newClient();
        webTarget = client.target(BASE_URL);
    }
    
    
    public void createChat(Cliente cliente, Refugio refugio, Solicitudadopcion solicitud){
        
        
    }
    
    
    
    
}
