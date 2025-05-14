
package es.uva.petadopt.client;

import es.uva.petadopt.model.Mascota;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


public class MascotaRestClient {
    
    private static final String BASE_URL = "http://localhost:8080/PetAdopt_PSE/webresources/mascotas";
    private Client client;
    private WebTarget webTarget;

    public MascotaRestClient() {
        client = ClientBuilder.newClient();
        webTarget = client.target(BASE_URL);
    }
    
    public Mascota find(Integer id) {
        Response response = webTarget
                .path(String.valueOf(id))
                .request(MediaType.APPLICATION_JSON)
                .get();

        if (response.getStatus() != 200) {
            throw new RuntimeException("Error al obtener la mascota con id " + id + ": " + response.getStatus());
        }

        return response.readEntity(Mascota.class);
    }

    
    public List<Mascota> findAll() {
        Response response = webTarget
                .request(MediaType.APPLICATION_JSON)
                .get();

        if (response.getStatus() != 200) {
            throw new RuntimeException("Error al obtener todas las mascotas: " + response.getStatus());
        }

        return response.readEntity(new GenericType<List<Mascota>>() {
        });
    }
   
    
    public List<Mascota> findByEspecie(String especie) {
        WebTarget target = webTarget.path("especie").path(especie);

        Response response = target
                .request(MediaType.APPLICATION_JSON)
                .get();

        if (response.getStatus() != 200) {
            throw new RuntimeException("Error al obtener las mascotas por especie: " + response.getStatus());
        }

        return response.readEntity(new GenericType<List<Mascota>>() {
        });
    }
    
    public List<Mascota> findByRaza(String raza) {
        WebTarget target = webTarget.path("raza").path(raza);

        Response response = target
                .request(MediaType.APPLICATION_JSON)
                .get();

        if (response.getStatus() != 200) {
            throw new RuntimeException("Error al obtener las mascotas por raza: " + response.getStatus());
        }

        return response.readEntity(new GenericType<List<Mascota>>() {
        });
    }
    
    public List<Mascota> findByNombre(String nombre) {
        WebTarget target = webTarget.path("nombre").path(nombre);

        Response response = target
                .request(MediaType.APPLICATION_JSON)
                .get();

        if (response.getStatus() != 200) {
            throw new RuntimeException("Error al obtener las mascotas por nombre: " + response.getStatus());
        }

        return response.readEntity(new GenericType<List<Mascota>>() {
        });
    }
    
    public List<String> obtenerEspecies() {
        Response response = webTarget
                .path("especies")
                .request(MediaType.APPLICATION_JSON)
                .get();

        if (response.getStatus() != 200) {
            throw new RuntimeException("Error al obtener las especies: " + response.getStatus());
        }

        return response.readEntity(new GenericType<List<String>>() {
        });
    }
   
    
    public List<String> obtenerRazasPorEspecie(String especie) {
        Response response = webTarget
                .path("razas")
                .path(especie)
                .request(MediaType.APPLICATION_JSON)
                .get();

        if (response.getStatus() != 200) {
            throw new RuntimeException("Error al obtener las razas para la especie '" + especie + "': " + response.getStatus());
        }

        return response.readEntity(new GenericType<List<String>>() {
        });
    }

    
    
    
    
    
    
}
