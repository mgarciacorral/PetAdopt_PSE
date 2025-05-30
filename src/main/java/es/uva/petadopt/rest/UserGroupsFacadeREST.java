package es.uva.petadopt.rest;

import es.uva.petadopt.model.UserGroups;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;

/**
 *
 * @author mgarc
 */
@Stateless
@Path("es.uva.petadopt.model.usergroups")
public class UserGroupsFacadeREST extends AbstractFacade<UserGroups> {

    @PersistenceContext(unitName = "PetAdoptPU")
    private EntityManager em;



    public UserGroupsFacadeREST() {
        super(UserGroups.class);
    }

    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(UserGroups entity) {
        super.create(entity);
    }
    
    @DELETE
    @Path("{email}")
    @Produces(MediaType.TEXT_PLAIN)
    public void delete(@PathParam("email") String email) {
        em.createQuery(
                "DELETE FROM UserGroups ug WHERE ug.userGroupsPK.email = :mail")
                .setParameter("mail", email)
                .executeUpdate();
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") PathSegment id, UserGroups entity) {
        super.edit(entity);
    }



    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<UserGroups> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<UserGroups> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}
