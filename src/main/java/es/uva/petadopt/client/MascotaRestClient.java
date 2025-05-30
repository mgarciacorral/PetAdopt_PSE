
package es.uva.petadopt.client;

import es.uva.petadopt.model.Mascota;
import es.uva.petadopt.model.Solicitudadopcion;
import java.io.ByteArrayInputStream;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Named
@Dependent
public class MascotaRestClient {
    
    private static final String BASE_URL = "http://localhost:8080/PetAdopt_PSE/webresources/mascotas";
    private final Client client;
    private final WebTarget webTarget;

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
    
    public ByteArrayInputStream getFoto(Mascota mascota) {
        int id = mascota.getIdMascota();

        Response response = webTarget
                .path("imagen")
                .path(String.valueOf(id))
                .request()
                .get(); // pedimos imagen directamente, no como JSON

        if (response.getStatus() != 200) {
            return null;
        }

        byte[] imagenBytes = response.readEntity(byte[].class);
        return new ByteArrayInputStream(imagenBytes);
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
    
    public List<Mascota> findByRefugio(String refugio) {
        WebTarget target = webTarget.path("refugio").path(refugio);

        Response response = target
                .request(MediaType.APPLICATION_JSON)
                .get();

        if (response.getStatus() != 200) {
            throw new RuntimeException("Error al obtener las mascotas por refugio: " + response.getStatus());
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
    
    public void crearMascota(Mascota mascota) {
        WebTarget resource = webTarget;
        resource.request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(mascota, MediaType.APPLICATION_JSON));
    }   
    
    public void borrarMascota(Mascota mascota) {
        int id = mascota.getIdMascota();
        Response response = webTarget.path(String.valueOf(id)).request().delete();

        if (response.getStatus() == 204) {
            System.out.println("Solicitud de adopción eliminada correctamente.");
            SolicitudRestClient solRest = new SolicitudRestClient();
            solRest.borrarPorMascota(mascota);
        } else {
            System.out.println("Error al eliminar la solicitud. Código: " + response.getStatus());
        }

        response.close();
    }
    
    public void editarMascota(Mascota mascota) {
        Response response = webTarget
                .path(String.valueOf(mascota.getIdMascota()))
                .request()
                .put(Entity.entity(mascota, MediaType.APPLICATION_JSON));

        if (response.getStatus() == 200 || response.getStatus() == 204) {
            System.out.println("Mascota editada con éxito.");
        } else {
            System.err.println("Error al editar la mascota: " + response.getStatus());
        }
    }
    
    public void eliminarPorRefugio(String email){
        List<Mascota> mascotas = findByRefugio(email);
        mascotas.forEach(mascota -> {
            borrarMascota(mascota);
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
