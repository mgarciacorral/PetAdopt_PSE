
package es.uva.petadopt.client;

import es.uva.petadopt.model.Mensaje;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

public class MensajeRestClient {
    
    
    private static final String BASE_URL = "http://localhost:8080/PetAdopt_PSE/webresources/mensaje";
    private final Client client;
    private final WebTarget webTarget;

    public MensajeRestClient() {
        client = ClientBuilder.newClient();
        webTarget = client.target(BASE_URL);
    }
    
    
    public List<Mensaje> findMensajesRefugio(int idChat, String emailRefugio) {
        
        WebTarget target = webTarget.path("mensajes").path("refugio").path(String.valueOf(idChat)).path(emailRefugio);
        
        Response response = target.request().get();
        
        if (response.getStatus() == 200) {
            List<Mensaje> mensajes = response.readEntity(new GenericType<List<Mensaje>>() {
            });
            return mensajes;
        } else {
            throw new RuntimeException("Error al obtener mensajes: " + response.getStatus());
        }

    }

    public List<Mensaje> findMensajesCliente(int idChat, String emailCliente) {
        
        WebTarget target = webTarget.path("mensajes").path("cliente").path(String.valueOf(idChat)).path("emailCliente");

        Response response = target.request().get();

        if (response.getStatus() == 200) {
            List<Mensaje> mensajes = response.readEntity(new GenericType<List<Mensaje>>() {
            });
            return mensajes;
        } else {
            throw new RuntimeException("Error al obtener mensajes: " + response.getStatus());
        }
    }
    
}
