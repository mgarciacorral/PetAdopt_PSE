
package es.uva.petadopt.client;

import es.uva.petadopt.dto.ChatDTO;
import es.uva.petadopt.model.Mensaje;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ChatRestClient {
    
    private static final String BASE_URL = "http://localhost:8080/PetAdopt_PSE/webresources/chats";
    private final Client client;
    private final WebTarget webTarget;

    public ChatRestClient() {
        client = ClientBuilder.newClient();
        webTarget = client.target(BASE_URL);
    }
    
    
    public void createChat(String emailCliente, String emailRefugio, int idSolicitud) {
        ChatDTO dto = new ChatDTO();
        dto.setClienteId(emailCliente);
        dto.setRefugioId(emailRefugio);
        dto.setSolicitudId(idSolicitud);

        Response response = webTarget
                .path("create")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(dto, MediaType.APPLICATION_JSON));
        
        if (response.getStatus() == 201) {
            System.out.println("Chat creado con éxito.");
        } else {
            System.err.println("Error al crear el chat: " + response.getStatus());
        }
    }
    
    
    public List<Mensaje> findMensajesRefugio(int idChat){
        
    }
    
    public List<Mensaje> findMensajesCliente(int idChat) {

    }
}
