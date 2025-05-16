
package es.uva.petadopt.rest;

import es.uva.petadopt.model.Mascota;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Stateless
@Path("mascotas")
public class MascotaFacadeREST extends AbstractFacade<Mascota> {

    @PersistenceContext(unitName = "PetAdoptPU")
    private EntityManager em;
    
    @Context
    private ServletContext servletContext;

    public MascotaFacadeREST() {
        super(Mascota.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Mascota entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Mascota entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }
    
    @GET
    @Path("imagen/{id}")
    @Produces({"image/png", "image/jpeg", "image/jpg"})
    public Response obtenerImagen(@PathParam("id") Integer id) throws IOException {
        Mascota mascota = super.find(id);
        byte[] imagenBytes;

        if (mascota != null && mascota.getFoto() != null) {
            imagenBytes = mascota.getFoto();
        } else {
            String ruta = servletContext.getRealPath("/resources/img/default.png");

            File archivo = new File(ruta);
            if (!archivo.exists()) {
                return Response.status(Response.Status.NOT_FOUND).entity("Imagen por defecto no encontrada").build();
            }
            imagenBytes = Files.readAllBytes(archivo.toPath());
        }

        return Response.ok(new ByteArrayInputStream(imagenBytes))
                .type("image/png") // Ajustá según el formato real
                .build();
    }


    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Mascota find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Mascota> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Mascota> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }
    
    @GET
    @Path("especie/{especie}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Mascota> findByEspecie(@PathParam("especie") String especie) {
        return em.createQuery("SELECT m FROM Mascota m WHERE m.especie = :especie", Mascota.class)
                .setParameter("especie", especie)
                .getResultList();
    }

    @GET
    @Path("raza/{raza}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Mascota> findByRaza(@PathParam("raza") String raza) {
        return em.createQuery("SELECT m FROM Mascota m WHERE m.raza = :raza", Mascota.class)
                .setParameter("raza", raza)
                .getResultList();
    }

    @GET
    @Path("nombre/{nombre}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Mascota> buscarMascotasPorNombre(@PathParam("nombre") String nombre) {
        return em.createQuery("SELECT m FROM Mascota m WHERE LOWER(m.nombre) LIKE :nombre", Mascota.class)
                .setParameter("nombre", "%" + nombre.toLowerCase() + "%")
                .getResultList();
    }
    
    @GET
    @Path("refugio/{refugio}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Mascota> buscarMascotasPorRefugio(@PathParam("refugio") String refugio) {
        return em.createQuery("SELECT m FROM Mascota m WHERE LOWER(m.emailRefugio) LIKE :refugio", Mascota.class)
                .setParameter("refugio", "%" + refugio.toLowerCase() + "%")
                .getResultList();
    }

    @GET
    @Path("especies")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<String> obtenerEspecies() {
        return em.createQuery("SELECT DISTINCT m.especie FROM Mascota m", String.class)
                .getResultList();
    }

    @GET
    @Path("razas/{especie}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<String> obtenerRazasPorEspecie(@PathParam("especie") String especie) {
        return em.createQuery("SELECT DISTINCT m.raza FROM Mascota m WHERE m.especie = :especie", String.class)
                .setParameter("especie", especie)
                .getResultList();
    }


    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
