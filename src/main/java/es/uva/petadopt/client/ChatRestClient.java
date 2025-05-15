
package es.uva.petadopt.client;

import es.uva.petadopt.dto.ChatDTO;
import es.uva.petadopt.model.Chat;
import es.uva.petadopt.model.Mascota;
import es.uva.petadopt.model.Solicitudadopcion;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
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
    
    public List<Chat> findBySolicitud(Solicitudadopcion sol) {
        WebTarget target = webTarget.path("por-solicitud").path(String.valueOf(sol.getIdSolicitud()));

        Response response = target
                .request(MediaType.APPLICATION_JSON)
                .get();

        if (response.getStatus() != 200) {
            throw new RuntimeException("Error al obtener las mascotas solicitadas: " + response.getStatus());
        }

        return response.readEntity(new GenericType<List<Chat>>() {
        });
    }
    
    public void borrarChat(Chat chat) {
        int id = chat.getIdSolicitud();
        Response response = webTarget.path(String.valueOf(id)).request().delete();

        if (response.getStatus() == 204) {
            System.out.println("Solicitud de adopción eliminada correctamente.");
        } else {
            System.out.println("Error al eliminar la solicitud. Código: " + response.getStatus());
        }

        response.close();
        client.close();
    }
    
    public void borrarPorSolicitud(Solicitudadopcion sol){
        List<Chat> chats = findBySolicitud(sol);
        chats.forEach(chat -> {
            borrarChat(chat);
        });
    }
}
