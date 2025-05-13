
package es.uva.petadopt.client;

import es.uva.petadopt.model.Cliente;
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


public class SolicitudRestClient {
    
    private static final String BASE_URL = "http://localhost:8080/PetAdopt_PSE/webresources/solicitudes";
    private Client client;
    private WebTarget webTarget;

    public SolicitudRestClient() {
        client = ClientBuilder.newClient();
        webTarget = client.target(BASE_URL);
    }

    public void createSolicitud(Solicitudadopcion solicitud) {
        Response response = webTarget
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(solicitud, MediaType.APPLICATION_JSON));
    }

    public Solicitudadopcion find(String email) {
        return client
                .target(BASE_URL)
                .path(email)
                .request(MediaType.APPLICATION_JSON)
                .get(Solicitudadopcion.class);
    }
    
    public List<Mascota> findSolicitadas(String emailCliente) {
        WebTarget target = webTarget.path("solicitadas").path(emailCliente);

        Response response = target
                .request(MediaType.APPLICATION_JSON)
                .get();

        if (response.getStatus() != 200) {
            throw new RuntimeException("Error al obtener las mascotas solicitadas: " + response.getStatus());
        }

        return response.readEntity(new GenericType<List<Mascota>>() {
        });
    }



}
