
package es.uva.petadopt.client;

import es.uva.petadopt.model.Cliente;
import es.uva.petadopt.model.Mascota;
import es.uva.petadopt.model.Solicitudadopcion;
import es.uva.petadopt.dto.SolicitudDTO;
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
    private final Client client;
    private final WebTarget webTarget;

    public SolicitudRestClient() {
        client = ClientBuilder.newClient();
        webTarget = client.target(BASE_URL);
    }

    public void createSolicitud(Cliente cliente, Mascota mascota) {
        
        
        SolicitudDTO dto = new SolicitudDTO();
        dto.setCliente(cliente);
        dto.setMascota(mascota);
        
        
        Response response = webTarget
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(dto, MediaType.APPLICATION_JSON));
        
        if (response.getStatus() == 200 || response.getStatus() == 204) {
            System.out.println("Solicitud creada con éxito");
        } else {
            System.err.println("Error al crear solicitud: " + response.getStatus());
        }
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
    
    public boolean comprobarSolicitud(String emailCliente, int idMascota) {
        Response response = webTarget
                .path("comprobar")
                .path(emailCliente)
                .path(String.valueOf(idMascota))
                .request(MediaType.TEXT_PLAIN)
                .get();

        if (response.getStatus() != 200) {
            throw new RuntimeException("Error al comprobar la solicitud: " + response.getStatus());
        }

        String result = response.readEntity(String.class);
        return Boolean.parseBoolean(result);
    }
    
    public Solicitudadopcion getLastSolicitudId(String emailCliente, int idMascota) {
        return webTarget
                .path("ultima")
                .path(emailCliente)
                .path(String.valueOf(idMascota))
                .request(MediaType.APPLICATION_JSON)
                .get(Solicitudadopcion.class);
    }
    
    public List<Mascota> findByMascota(Mascota mascota) {
        WebTarget target = webTarget.path("por-mascota").path(String.valueOf(mascota.getIdMascota()));

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
